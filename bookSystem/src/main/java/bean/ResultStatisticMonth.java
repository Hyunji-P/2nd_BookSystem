package bean;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResultStatisticMonth {
	private String email; // 이메일 
	private Map<Integer, Integer> contentMap;
	private int sumCount; // 총 건수 
}
