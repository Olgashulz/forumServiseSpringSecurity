package telran.java2022.post.service.login;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;
//import org.aspect.lang.annotation.Acpect;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Service
@Slf4j
public class PostServiceLogin {

	@Pointcut("execution(* telran.java2022.post.service.PostServiceImpl.getPost(String))" + " && args(id))")
	public void findById(String id) {
	}

	@Pointcut("execution(public Iterable<telran.java2022.post.dto.PostDto>"
			+ "telran.java2022.post.service.PostServiceImpl.findPosts*(..))")
	public void bulkinFind() {}
	
	@Pointcut("@annotation(PostLogger) && args(.., id)")
	public void annotated(String id) {}
	
//@Before
	public void getPostLogining(String id) {
		log.info("post with id {} request", id);

	}
	
	@Around("bulkinFind()")
	public Object bulkinFindLogging( ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		Long t1 = System.currentTimeMillis();
		Object retVal= pjp.proceed(args);
		Long t2 = System.currentTimeMillis();
		log.info("method - {}, duration = {}", pjp.getSignature().toLongString(),(t2-t1));
		return retVal;
	}
	
	@AfterReturning("annotated(id)")
	public void changePostLogging(String id) {
		log.info("post with id {} changed", id);
	}

}
