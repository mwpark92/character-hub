package team.spy.domain.common.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import team.spy.domain.common.dto.Count;
import team.spy.domain.common.dto.CountProjectId;
import team.spy.domain.common.repository.CountRepository;


/**
 * 카운트와 관련된 클래스
 * 가져오기, 추천 등을 담당한다.
 * @author kwanghoyeom
 *
 */
@Service
public class CountService {

	@Autowired
	CountRepository countRepository;
	
	@Autowired
	RedisTemplate<String, String> redisTemplate;
	
	// 추후 변경 필요,type 0 : user / 1 : board
	// 조회수 가져오기 
	public int getCount(int type, int id)
	{
		if(redisTemplate.opsForValue().get("count:board:" + id) != null)
			return Integer.parseInt(redisTemplate.opsForValue().get("count:board:" + id));

		System.out.println("not use cache!");
		
		CountProjectId countProjectId = new CountProjectId(type, id);
		Count count = countRepository.findByCountProjectId(countProjectId);
	
		if(count == null) 
			count = countRepository.save(new Count(new CountProjectId(type, id), 0));
			
		return count.getCount();
		
	}
	
	public void increaseCount(int type, int id)
	{
		Count currentCount = countRepository.findByCountProjectId(new CountProjectId(type, id));
		currentCount.increaseCount();
		countRepository.save(currentCount);
		
		redisTemplate.opsForValue().set("count:board:" + id, String.valueOf(currentCount.getCount()));
	}
	
	public List<Count> getRankedList(int count)
	{
		
		return null;
	}
}
