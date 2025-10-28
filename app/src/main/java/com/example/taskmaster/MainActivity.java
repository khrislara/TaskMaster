package com.example.TaskMaster;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tvTitle, tvCardTitle, tvCardContent;
    private Button btnSubmitTask;
    private ImageView imageViewHeader;
    private CheckBox cbTaskCompleted;
    private RadioGroup rgTaskType;
    private ProgressBar progressBarTaskProgress;
    private RatingBar ratingBarTaskPriority;
    private Spinner spinnerTaskCategories;
    private CardView cardViewInfo;
    private TableLayout tableLayoutSummary;
    private RecyclerView recyclerViewTasks;

    private TaskAdapter taskAdapter;
    private ArrayList<TaskItem> tasksListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupTaskSpinner();
        setupRecyclerViewTasks();
        setupListeners();
    }

    private void initViews() {
        tvTitle = findViewById(R.id.tvTitle);
        btnSubmitTask = findViewById(R.id.btnSubmit);
        imageViewHeader = findViewById(R.id.imageViewHeader);
        cbTaskCompleted = findViewById(R.id.cbTerms);
        rgTaskType = findViewById(R.id.rgOptions);
        progressBarTaskProgress = findViewById(R.id.progressBar);
        ratingBarTaskPriority = findViewById(R.id.ratingBar);
        spinnerTaskCategories = findViewById(R.id.spinnerCategories);
        cardViewInfo = findViewById(R.id.cardViewInfo);
        tvCardTitle = findViewById(R.id.tvCardTitle);
        tvCardContent = findViewById(R.id.tvCardContent);
        tableLayoutSummary = findViewById(R.id.tableLayoutSummary);
        recyclerViewTasks = findViewById(R.id.recyclerViewItems);
    }

    private void setupTaskSpinner() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Trabajo");
        categories.add("Personal");
        categories.add("Estudios");
        categories.add("Casa");
        categories.add("Proyectos");
        categories.add("Urgente");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTaskCategories.setAdapter(adapter);
    }

    private void setupRecyclerViewTasks() {
        tasksListData = new ArrayList<>();
        taskAdapter = new TaskAdapter(tasksListData);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTasks.setAdapter(taskAdapter);
    }

    private void setupListeners() {
        ratingBarTaskPriority.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                progressBarTaskProgress.setProgress((int) (rating * 20));
            }
        });

        btnSubmitTask.setOnClickListener(v -> addNewTaskFromInputs());

        cardViewInfo.setOnClickListener(v ->
                Toast.makeText(MainActivity.this,
                        tvCardContent.getText(), Toast.LENGTH_LONG).show());
    }

    private void addNewTaskFromInputs() {
        String taskName = "Nueva tarea";
        boolean isCompleted = cbTaskCompleted.isChecked();

        String taskType = "";
        int selectedRadioId = rgTaskType.getCheckedRadioButtonId();
        if (selectedRadioId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioId);
            taskType = selectedRadioButton.getText().toString().trim();
        } else {
            Toast.makeText(this, "Selecciona un tipo de tarea", Toast.LENGTH_SHORT).show();
            return;
        }

        float priorityRating = ratingBarTaskPriority.getRating();
        String category = spinnerTaskCategories.getSelectedItem().toString();

        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        String priorityText;
        if (priorityRating <= 1.5) priorityText = "Baja";
        else if (priorityRating <= 3.5) priorityText = "Media";
        else priorityText = "Muy importante";

        String tareaFinal = taskName + (isCompleted ? " (Completada)" : "");

