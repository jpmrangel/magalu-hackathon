package br.com.jprangel.task_manager.usecases.task;

import br.com.jprangel.task_manager.model.TaskEntity;
import br.com.jprangel.task_manager.repository.TaskRepository;
import br.com.jprangel.task_manager.usecases.EmailUseCaseManager;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Stream;

@Service
public class RemindeTaskUseCase {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    EmailUseCaseManager emailUseCaseManager;

    @Scheduled(fixedRate = 60000)
    @Transactional(readOnly = true)
    public void checkAndSendReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime limitTime = now.plusMinutes(65);

        try (Stream<TaskEntity> tasksToRemind = taskRepository.findTasksForReminder(limitTime)) {
            tasksToRemind.forEach(task -> {
                LocalDateTime reminderTime = task.getExpectedFinishingDate().minusMinutes(task.getReminderTime());
                if (reminderTime.isBefore(LocalDateTime.now()) || reminderTime.isEqual(LocalDateTime.now())) {}
                String message = "Você tem uma tarefa que precisa ser finalizada em " + task.getReminderTime() + " minutos!!";
                try {
                    emailUseCaseManager.sendEmail("vitortaira@estudante.ufscar.br", "Lembrete de Tarefa", message);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
