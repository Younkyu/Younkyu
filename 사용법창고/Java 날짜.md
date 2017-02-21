###**Java 날짜 측정**

**java8 gradle plus**

		public static void adjustDate() {
			LocalDate today = LocalDate.now();
			LocalDate next = today.with(TemporalAdjusters.dayOfWeekInMonth(2, DayOfWeek.SUNDAY));
			
			System.out.printf("이번달 2번째 일요일:%s, \n", next);
		}

 결과값 : 이번달 2번째 일요일:2017-02-12
		
		
		public static void getDate() {
			LocalDate today = LocalDate.now();
			System.out.println("today:"+today);
			LocalDate myBirthday = LocalDate.of(1992, 6, 16);
			System.out.println("myBirthday:"+myBirthday);
			myBirthday = LocalDate.of(1985, Month.APRIL, 16);
			System.out.println("mybirthday:"+myBirthday);
		}

 결과값 : today:2017-02-20
myBirthday:1992-06-16
mybirthday:1985-04-16
