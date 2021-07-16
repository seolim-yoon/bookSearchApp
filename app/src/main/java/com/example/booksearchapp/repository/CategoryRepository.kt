package com.example.booksearchapp.repository

class CategoryRepository {
    val domesticList = arrayListOf<String>("ALL", "소설", "시/에세이", "예술/대중문화", "사회과학", "역사와 문화", "잡지", "만화", "유아", "아동", "가정과 생활")
    val foreignList = arrayListOf<String>("ALL", "어린이", "ELT/사전", "문학", "경영/인문", "예술/디자인", "실용", "해외잡지", "대학교재", "컴퓨터", "일본도서")
    val recordList = arrayListOf<String>("ALL", "가요", "Pop", "Rock", "일본음악", "World Music", "Jazz", "클래식", "국악", "명상", "O.S.T")
    val dvdList = arrayListOf<String>("ALL", "애니메이션", "다큐멘터리", "TV 시리즈", "건강/스포츠", "영화", "해외구매", "기타", "", "", "")
}