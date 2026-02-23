package com.prm.flightbooking;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.noties.markwon.Markwon;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AIPlannerActivity extends AppCompatActivity {

    private EditText etDestination, etDuration, etInterests;
    private Button btnGeneratePlan;
    private ProgressBar progressBar;
    private TextView tvResult, tvError;
    private ImageButton btnBack;
    private OkHttpClient client;
    private Gson gson;

    // ── Groq API config ──────────────────────────────────────────────────
    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";

    private static final String MODEL = "llama-3.3-70b-versatile";  // Nhanh nhất: llama-3.1-8b-instant | Chất lượng cao: llama-3.3-70b-versatile | gemma2-9b-it
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_planner);

        etDestination = findViewById(R.id.etDestination);
        etDuration = findViewById(R.id.etDuration);
        etInterests = findViewById(R.id.etInterests);
        btnGeneratePlan = findViewById(R.id.btnGeneratePlan);
        progressBar = findViewById(R.id.progressBar);
        tvResult = findViewById(R.id.tvResult);
        tvError = findViewById(R.id.tvError);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        client = new OkHttpClient.Builder()
                .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
                .writeTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
                .build();

        gson = new Gson();

        btnGeneratePlan.setOnClickListener(v -> {
            String destination = etDestination.getText().toString().trim();
            String duration = etDuration.getText().toString().trim();
            String interests = etInterests.getText().toString().trim();

            if (TextUtils.isEmpty(destination) || TextUtils.isEmpty(duration)) {
                Toast.makeText(this, "Vui lòng nhập Điểm đến và Số ngày.", Toast.LENGTH_SHORT).show();
                return;
            }

            generatePlan(destination, duration, interests);
        });
    }

    private void generatePlan(String destination, String duration, String interests) {
        progressBar.setVisibility(View.VISIBLE);
        tvResult.setText("");
        tvError.setVisibility(View.GONE);
        btnGeneratePlan.setEnabled(false);

        String userPrompt = String.format(
                "Tạo một lịch trình du lịch chi tiết cho chuyến đi %s ngày tại %s. Người đi có sở thích về %s. " +
                        "Vui lòng trả lời bằng tiếng Việt, sử dụng định dạng Markdown với tiêu đề cho mỗi ngày " +
                        "và gạch đầu dòng cho các hoạt động cụ thể. Bao gồm gợi ý ăn uống, di chuyển nếu phù hợp.",
                duration, destination,
                interests.isEmpty() ? "khám phá và trải nghiệm văn hóa địa phương" : interests
        );

        // Messages giống OpenAI
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("system", "Bạn là một chuyên gia lập kế hoạch du lịch chuyên nghiệp, trả lời chi tiết, thực tế và hấp dẫn bằng tiếng Việt."));
        messages.add(new Message("user", userPrompt));

        GroqRequest requestObj = new GroqRequest(MODEL, messages, 0.7, 1500); // temperature 0.7, max_tokens 1500

        String jsonRequest = gson.toJson(requestObj);

        RequestBody body = RequestBody.create(jsonRequest, JSON);

        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + GROQ_API_KEY)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    btnGeneratePlan.setEnabled(true);
                    showError("Lỗi kết nối: " + e.getMessage());
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    btnGeneratePlan.setEnabled(true);
                });

                if (!response.isSuccessful()) {
                    String errorBody = response.body() != null ? response.body().string() : "";
                    final String msg;
                    if (response.code() == 429) {
                        msg = "Quá nhiều yêu cầu (429). Groq free tier giới hạn ~30 req/phút. Đợi 1-2 phút rồi thử lại.";
                    } else if (response.code() == 401) {
                        msg = "API Key Groq không hợp lệ. Kiểm tra lại GROQ_API_KEY.";
                    } else {
                        msg = "Lỗi Groq API: " + response.code() + "\n" + errorBody;
                    }
                    runOnUiThread(() -> showError(msg));
                    return;
                }

                String responseBodyStr = response.body().string();
                GroqResponse groqResponse = gson.fromJson(responseBodyStr, GroqResponse.class);

                if (groqResponse != null && groqResponse.choices != null && !groqResponse.choices.isEmpty()) {
                    String text = groqResponse.choices.get(0).message.content;

                    runOnUiThread(() -> {
                        Markwon markwon = Markwon.create(AIPlannerActivity.this);
                        markwon.setMarkdown(tvResult, text);
                        tvError.setVisibility(View.GONE);
                    });
                } else {
                    runOnUiThread(() -> showError("Không nhận được nội dung hợp lệ từ Groq."));
                }
            }
        });
    }

    private void showError(String message) {
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
    }

    // ── Model classes (giống OpenAI, nên dùng chung) ─────────────────────
    public static class GroqRequest {
        @SerializedName("model")
        public final String model;

        @SerializedName("messages")
        public final List<Message> messages;

        @SerializedName("temperature")
        public final double temperature;

        @SerializedName("max_tokens")
        public final int maxTokens;

        public GroqRequest(String model, List<Message> messages, double temperature, int maxTokens) {
            this.model = model;
            this.messages = messages;
            this.temperature = temperature;
            this.maxTokens = maxTokens;
        }
    }

    public static class Message {
        @SerializedName("role")
        public final String role;

        @SerializedName("content")
        public final String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }

    public static class GroqResponse {
        @SerializedName("choices")
        public List<Choice> choices;
    }

    public static class Choice {
        @SerializedName("message")
        public Message message;
    }
}