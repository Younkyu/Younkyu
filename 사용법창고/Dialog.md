##**Dialog**


	AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
	
	                            alertDialog.setTitle("삭제");
	                            alertDialog.setMessage("수정 또는 삭제 \n 버튼을 눌러주세요");
	                            //ok를 누르게 되면 설정창으로 이동합니다.
	                            // 예스버튼 생성
	                            alertDialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
	                                @Override
	                                public void onClick(DialogInterface dialog, int which) {
	
	                                    Toast.makeText(context,"삭제했습니다.", Toast.LENGTH_LONG).show();
	                                    dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
	                                    try {
	                                        memoDao = dbHelper.getMemoDao();
	                                        memoDao.delete(memo);
	
	                                        try {
	                                            datas = memoDao.queryForAll();
	                                        } catch (SQLException e) {
	                                            e.printStackTrace();
	                                        }
	                                        li.setData(datas);
	                                        li.refreshAdapter();
	                                    } catch (SQLException e) {
	                                        e.printStackTrace();
	                                    }
	                                }
	                            });
	                            // no 버튼생성
	                            alertDialog.setNegativeButton("수정", new DialogInterface.OnClickListener() {
	                                @Override
	                                public void onClick(DialogInterface dialog, int which) {
	
	                                    Bundle bundle = new Bundle();
	                                    if(memo.getContent() != null) {
	                                        bundle.putString("gl", memo.getContent());
	                                    }
	                                    if(memo.getUri() != null) {
	                                        bundle.putString("uri", memo.getUri());
	                                    }
	                                    bundle.putInt("no", memo.getId());
	                                    wr.setArguments(bundle);
	
	
	                                    FragmentTransaction transaction = manager.beginTransaction();
	                                    transaction.add(R.id.fragment, wr); // add가 잘 안먹어서 replace 많이씀
	                                    // 4. 트랜잭션 전체를 스택에 저장을 합니다.
	                                    transaction.addToBackStack(null);
	                                    // 5. git의 commit과 같은 기능
	                                    transaction.commit();
	
	
	
	
	
	                                    dialog.cancel();
	
	                                }
	                            });
	
	                            alertDialog.setNeutralButton("취소", new DialogInterface.OnClickListener() {
	                                @Override
	                                public void onClick(DialogInterface dialog, int which) {
	                                    dialog.cancel();
	                                }
	                            });
	                            //5. show함수로 팝업창에 화면에 띄운다.
	                            alertDialog.show();
	                            break;
	                            alertDialog.setNeutralButton("취소", new DialogInterface.OnClickListener() {
	                                @Override
	                                public void onClick(DialogInterface dialog, int which) {
	                                    dialog.cancel();
	                                }
	                            });
	                            //5. show함수로 팝업창에 화면에 띄운다.
	                            alertDialog.show();
	                            break;