package com.example.syntaxgo;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main activity that handles file selection, code displaying, and interaction.
 */
public class MainActivity extends AppCompatActivity {

    private WebView webViewCode;
    private String currentRawCode = "";

    // UI Layout containers
    private View layoutHome;
    private View layoutViewer;

    // --- BACKGROUND WORKERS (To prevent Main Thread freezing/ANR) ---
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    // Launcher to handle the result of the file selection Intent.
    private final ActivityResultLauncher<Intent> filePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    if (uri != null) {
                        // No blocking here. Passing the URI directly to the background worker.
                        processAndDisplayFileInBackground(uri);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Map XML IDs to Java views.
        layoutHome = findViewById(R.id.layoutHome);
        layoutViewer = findViewById(R.id.layoutViewer);
        webViewCode = findViewById(R.id.webViewCode);

        Button btnInitialSelect = findViewById(R.id.btnInitialSelect);
        Button btnTopSelect = findViewById(R.id.btnTopSelect);
        Button btnCopy = findViewById(R.id.btnCopy);

        // WebView settings for interaction (enable zoom).
        WebSettings webSettings = webViewCode.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false); // Hide displayed zoom buttons.

        // Shared click action for both file selection buttons.
        View.OnClickListener selectFileAction = v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            filePickerLauncher.launch(intent);
        };

        btnInitialSelect.setOnClickListener(selectFileAction);
        btnTopSelect.setOnClickListener(selectFileAction);

        // Copy button function: copies raw code to clipboard.
        btnCopy.setOnClickListener(v -> {
            if (!currentRawCode.isEmpty()) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("SyntaxGo Code", currentRawCode);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(MainActivity.this, "Code copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        // --- HANDLE EXTERNAL FILE INTENTS (OPEN WITH) ---
        Intent externalIntent = getIntent();
        if (Intent.ACTION_VIEW.equals(externalIntent.getAction()) && externalIntent.getData() != null) {
            Uri externalUri = externalIntent.getData();
            // Utilizing the background worker here as well to keep the UI thread smooth.
            processAndDisplayFileInBackground(externalUri);
        }
    }

    /**
     * Reads the file asynchronously in the background and updates the WebView when done.
     * This architecture ensures the UI never freezes, even with large files.
     */
    private void processAndDisplayFileInBackground(Uri uri) {
        // Show a loading indicator (crucial for large file reads)
        Toast.makeText(this, "Reading file...", Toast.LENGTH_SHORT).show();

        // 1. Offload the heavy I/O operations to the background thread
        executorService.execute(() -> {
            StringBuilder stringBuilder = new StringBuilder();
            try (InputStream inputStream = getContentResolver().openInputStream(uri);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
                final String errorMsg = "Error opening file: " + e.getMessage();
                // If an exception occurs, we must return to the Main Thread to show the Toast safely
                mainThreadHandler.post(() -> Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_LONG).show());
                return;
            }

            final String finalCode = stringBuilder.toString();

            // 2. I/O complete! Take the extracted code and return to the Main (UI) Thread
            mainThreadHandler.post(() -> {
                currentRawCode = finalCode;

                // Sanitize and generate HTML, then load into WebView
                String htmlContent = generateHtmlWithHighlightJs(currentRawCode);
                webViewCode.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null);

                // Hide the home layout and display the code viewer
                layoutHome.setVisibility(View.GONE);
                layoutViewer.setVisibility(View.VISIBLE);
            });
        });
    }

    /**
     * Generates a dynamic HTML string with syntax highlighting and line numbers.
     * Uses assets stored locally for offline capability and htmlEncode for XSS prevention.
     */
    private String generateHtmlWithHighlightJs(String code) {

        // Proper sanitization to prevent XSS and HTML parsing errors in WebView
        String safeCode = android.text.TextUtils.htmlEncode(code);

        // The HTML template with embedded styling and highlighting logic.
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <link rel=\"stylesheet\" href=\"file:///android_asset/vs2015.min.css\">\n" +
                "    <script src=\"file:///android_asset/highlight.min.js\"></script>\n" +
                "    <script src=\"file:///android_asset/highlightjs-line-numbers.min.js\"></script>\n" +
                "    <script>\n" +
                "        hljs.highlightAll();\n" +
                "        hljs.initLineNumbersOnLoad();\n" +
                "    </script>\n" +
                "    <style>\n" +
                "        body { background-color: #1E1E1E; margin: 0; padding: 12px; }\n" +
                "        pre { margin: 0; }\n" +
                "        code { font-family: monospace; font-size: 14px; }\n" +
                "        \n" +
                "        /* --- PROFESSIONAL LINE NUMBER DESIGN (VS CODE STYLE) --- */\n" +
                "        .hljs-ln-numbers {\n" +
                "            -webkit-touch-callout: none;\n" +
                "            -webkit-user-select: none;\n" +
                "            -khtml-user-select: none;\n" +
                "            -moz-user-select: none;\n" +
                "            -ms-user-select: none;\n" +
                "            user-select: none;\n" +
                "            text-align: right;          /* Align numbers to the right */\n" +
                "            color: #858585;\n" +
                "            border-right: 1px solid #404040; /* Line separator */\n" +
                "            vertical-align: top;\n" +
                "            padding-right: 12px !important;\n" +
                "            margin-right: 12px !important;\n" +
                "        }\n" +
                "        .hljs-ln-code {\n" +
                "            padding-left: 12px !important;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <pre><code>" + safeCode + "</code></pre>\n" +
                "</body>\n" +
                "</html>";
    }
}