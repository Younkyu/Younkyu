###**TimeUtil(메소드 시간 측정)**

 새로 클래스를 판 후
 
	public class TimeUtil {
	
		private Long start;
		private Long end;
		private Long period;
		
	
		// 현재시간 값을 저장한 TimeUtil instance를 반환
		public static TimeUtil setStart() {
			TimeUtil timeUtil = new TimeUtil();
			timeUtil.start = System.currentTimeMillis();
			return timeUtil;
		}
		
		// setstart에서 넘겨주는 timeUtil값을 반환
		public static void setEnd(TimeUtil timeUtil) {
			timeUtil.end  = System.currentTimeMillis();
			timeUtil.period = timeUtil.end - timeUtil.start;
			System.out.println("duration:" + timeUtil.period);
		}
		
	}

**본문 사용 방식**
	
	 TimeUtil timeUtil = TimeUtil.setStart();
			double r=0;
			for(Long i= 0l; i < 1000000l ; i ++) {
				r = r+i;
			}
			timeUtil.setEnd(timeUtil);

