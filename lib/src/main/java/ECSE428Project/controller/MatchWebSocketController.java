package ECSE428Project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.server.ResponseStatusException;
import ECSE428Project.dto.PostGameStatDto;
import ECSE428Project.dto.QuestionDto;
import ECSE428Project.model.Question;
import ECSE428Project.service.MatchService;

@Controller
@CrossOrigin(origins = "*")
public class MatchWebSocketController {

	@Autowired
	private MatchService matchService;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping("/game/toServer/{email}/nextQuestion")
	public void getNextQuestion(@DestinationVariable String email) {
		try {
			Question question = matchService.getNextQuestion(email);
			if (question == null) {
				QuestionDto dto = new QuestionDto();
				dto.setMessage("Game over");
				simpMessagingTemplate.convertAndSend("/game/toPlayer/" + email, dto);
			} else {
				simpMessagingTemplate.convertAndSend("/game/toPlayer/" + email, QuestionDto.toDto(question));
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@MessageMapping("/game/toServer/{email}/postGameStat")
	public void getPostGameStat(@DestinationVariable String email) {
		try {
			PostGameStatDto stat = matchService.getPostGameStat(email);
			if (stat == null) {
				simpMessagingTemplate.convertAndSend("/game/toPlayer/" + email, "error");
			} else {
				simpMessagingTemplate.convertAndSend("/game/toPlayer/" + email, stat);
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
