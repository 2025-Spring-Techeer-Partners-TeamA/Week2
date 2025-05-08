package daiseek.sbb;

import daiseek.sbb.entity.Answer;
import daiseek.sbb.entity.Question;
import daiseek.sbb.repository.AnswerRepository;
import daiseek.sbb.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Test
	void testJpa1() {
		Question q1 = new Question();
		q1.setSubject("sbb가 뭐야?");
		q1.setContent("sbb에 대해 알고 싶어!");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문이야");
		q2.setContent("id는 자동으로 생성돼?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
	}

	@Test
	void testJpa2() {
		List<Question> allQuestions = this.questionRepository.findAll();
		assertEquals(2, allQuestions.size());

		Question question = allQuestions.get(0);
		assertEquals("sbb가 뭐야?", question.getSubject());
	}

	@Test
	@Transactional
	void testJpa3() {
		Optional<Question> optionalQuestion = this.questionRepository.findById(1);

		if (optionalQuestion.isPresent()) {
			Question question = optionalQuestion.get();
			assertEquals("sbb가 뭐야?", question.getSubject());
		}
	}

	@Test
	@Transactional
	void testJpa4() {
		Question question = this.questionRepository.findBySubjectAndContent("sbb가 뭐야?", "sbb에 대해 알고 싶어!");
		assertEquals(1, question.getId());

	}

	@Test
	@Transactional
	void testJpa5() {
		List<Question> questions = this.questionRepository.findBySubjectLike("sbb%");
		Question question = questions.get(0);
		assertEquals("sbb가 뭐야?", question.getSubject());
	}

	@Test
	@Transactional
	void textJpa6() {
		Optional<Question> optionalQuestion = this.questionRepository.findById(1);
		assertTrue(optionalQuestion.isPresent());
		Question question = optionalQuestion.get();
		question.setSubject("수정된 제목");
		this.questionRepository.save(question);
	}

	@Test
	@Transactional
	void testJpa7() {
		Optional<Question> optionalQuestion = this.questionRepository.findById(2);
		assertTrue(optionalQuestion.isPresent());
		Question question = optionalQuestion.get();

		Answer answer = new Answer();
		answer.setContent("응! 자동으로 생성돼!");
		answer.setQuestion(question); // 어떤 질문의 답변인지 알기 위해 Question 객체가 필요
		answer.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(answer);
	}


	@Test
	@Transactional
	void contextLoads() {
	}

}
