package com.example.JFX_Controller;

import javafx.concurrent.Task;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncTaskExecutor {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(4); // Sử dụng 4 luồng trong pool

    /**
     * Thực hiện một tác vụ không trả về kết quả.
     *
     * @param task Logic cần thực thi trong luồng nền.
     * @param onSuccess Logic thực thi sau khi thành công (chạy trên UI thread).
     * @param onFailure Logic thực thi nếu có lỗi (chạy trên UI thread).
     */
    public static void executeAsync(final Runnable task, final Runnable onSuccess, final Runnable onFailure) {
        Task<Void> backgroundTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                task.run();
                return null;
            }
        };
        backgroundTask.setOnSucceeded(event -> {
            if (onSuccess != null) {
                onSuccess.run();
            }
        });

        backgroundTask.setOnFailed(event -> {
            if (onFailure != null) {
                onFailure.run();
            }
        });

        // Thay vì tạo một Thread mới, sử dụng ExecutorService để chạy tác vụ.
        executorService.submit(backgroundTask);
    }

    /**
     * Thực hiện một tác vụ trả về kết quả.
     *
     * @param <T> Loại kết quả trả về.
     * @param task Logic cần thực thi trong luồng nền.
     * @param onSuccess Logic thực thi sau khi thành công (chạy trên UI thread).
     * @param onFailure Logic thực thi nếu có lỗi (chạy trên UI thread).
     */
    public static <T> void executeAsync(final java.util.concurrent.Callable<T> task,
                                        final java.util.function.Consumer<T> onSuccess,
                                        final Runnable onFailure) {
        Task<T> backgroundTask = new Task<T>() {
            @Override
            protected T call() throws Exception {
                return task.call();
            }
        };

        backgroundTask.setOnSucceeded(event -> {
            if (onSuccess != null) {
                onSuccess.accept(backgroundTask.getValue());
            }
        });

        backgroundTask.setOnFailed(event -> {
            if (onFailure != null) {
                onFailure.run();
            }
        });

        // Sử dụng ExecutorService để chạy tác vụ với Callable.
        executorService.submit(backgroundTask);
    }

    /**
     * Dừng ExecutorService khi không còn sử dụng.
     */
    public static void shutdown() {
        executorService.shutdown();
    }
}
