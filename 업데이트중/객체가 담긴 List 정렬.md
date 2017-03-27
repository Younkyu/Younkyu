### **객체가 담긴 List 정렬**


	// 단순한 String,int리스트가 아닌 객체에 대한 정렬을 해야할 경우에 사용
	    public void sortTop(List<tutor> datas2) {
	        Collections.sort(datas2, new Comparator() {
	            @Override
	            public int compare(Object o1, Object o2) {
	                //사용하려는 객체로 파싱해줌
	                tutor no1 = (tutor)o1;
	                tutor no2 = (tutor)o2;
	
	                //-1과 1의 위치를 조정하면 오름차순/내림차순을 조절할 수 있다.
	                if(no1.getTutor_rating() > no2.getTutor_rating()) {
	                    return -1;
	                } else if (no1.getTutor_rating() == no2.getTutor_rating()){
	                    return 0;
	                } else {
	                    return 1;
	                }
	            }
	        });
	    }