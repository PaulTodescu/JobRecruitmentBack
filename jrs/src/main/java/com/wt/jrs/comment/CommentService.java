package com.wt.jrs.comment;

import com.wt.jrs.job.JobEntity;
import com.wt.jrs.job.JobService;
import com.wt.jrs.user.UserDTO;
import com.wt.jrs.user.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommentService {

    private final CommentDAO commentDAO;
    private final JobService jobService;
    private final UserService userService;

    public CommentService(CommentDAO commentDAO, JobService jobService, UserService userService) {
        this.commentDAO = commentDAO;
        this.jobService = jobService;
        this.userService = userService;
    }

    public void addComment(Long jobId, String content){
        JobEntity job = this.jobService.findJobById(jobId);

        LocalDateTime currentDate = LocalDateTime.now();

        UserDTO currentUser = this.userService.findLoggedInUser();
        String author = currentUser.getFirstName() + " " + currentUser.getLastName();

        CommentEntity comment = new CommentEntity(content, currentDate.toString(), author, job);
        commentDAO.save(comment);
    }

    public List<CommentEntity> getComments(Long jobId) {
        JobEntity job = this.jobService.findJobById(jobId);
        return List.copyOf(job.getComments());
    }
}
