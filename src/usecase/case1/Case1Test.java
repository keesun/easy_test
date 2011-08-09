package usecase.case1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import usecase.case1.domain.Function;
import usecase.case1.domain.Menu;
import usecase.case1.repository.FunctionRepository;
import usecase.case1.repository.MenuRepository;

/**
 * KSUG 7/22 딤딤이
 * 
 * JPA(Hibernate)를 실전에 처음 적용하면서 삽질을 하고 있는 ORM초보 개발자 입니다.

맨번 눈팅만 하다가 질문만 드리는게 민망하지만... 하루 온종일 하나의 문제로 씨름을 하다보니 너무 답답해서 질문을 올립니다.
ㅠ.ㅠ

현재 사항은 아래와 같습니다.
객체의 맵핑이 아래와 같이 단방향 OneToMay로 되어 있습니다.
@Entity
@Table(name = "CMS_MENU")
public class CmsMenu extends AbstractModel<Long> {
       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       @Column(name = "ID", nullable = false)
       private Long id;

       @OneToMany
       @JoinColumn(name = "MENU_ID")
       private List<CmsMenuFunction> functions = new
ArrayList<CmsMenuFunction>();
       .....
}

@Entity
@Table(name = "CMS_MENU_FUNCTION")
public class CmsMenuFunction extends AbstractModel<Long> {

       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       @Column(name = "ID", nullable = false)
       private Long id;

       @Column(name = "URL")
       private String url;

       @Column(name = "MENU_ID", nullable = false)
       private Long menuId;
       ......
}

하나 이상의 CMS_MENU_FUNCTION  데이터를 갖는 CMS_MENU 데이터를 삭제하려고 할때에 아래와 같은 에러가 발생
합니다.
 com.mysql.jdbc.MysqlDataTruncation: Data truncation: Column was set
to data type implicit default; NULL supplied for NOT NULL column
'MENU_ID' at row 1
위의 에러가 발생하기 전에 남는 SQL 로그는 아래와 같습니다. (CMS_MENU_FUNCTION 테이블의 MENU_ID 는
NOT NULL Column 입니다.)
 update CMS_MENU_FUNCTION set MENU_ID=null where MENU_ID=?

위의 문제를 해결하기 위해 두가지 방안을 찾았습니다.
1. 양방향 OneToMany 맵핑을 한후 @Cascade 사용
2. session.delete() 함수 대신 HQL을 사용하여 삭제함
   delete from CmsMenuFunction where menuId = :menuId
   delete from CmsMenu where id = :id

위의 방법들로 해결을 할 수는 있지만 제가 진정으로 원하는 방법은 단방향 OneToMany 맵핑시에도
session.delete() 함수를 사용하여 삭제전이가 일어나도록 하는 것입니다.
혹시 해당 방법을 아시는 분은 조언 부탁 드립니다.

 * @author root
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@Transactional
public class Case1Test {
	
	@Autowired MenuRepository menuRepository;
	@Autowired FunctionRepository functionRepository;
	
	/**
	 * 잘못된 양방향 설정이 문제였다.
완전한 양방향으로 설정하거나, 완전한 단방향으로 설정하면 아무 문제 없다.
아래 예제는 양방향 설정을 했을 때 동작을 확인해 봤다.
	 */
	@Test
	public void deleteMenu(){
		//GIVEN
		Function openFile = new Function();
		openFile.setName("open file");
		functionRepository.save(openFile);
		
		Menu menu = new Menu();
		menu.setName("keesun");
		menuRepository.save(menu);
		
		menu.addFunction(openFile);
		menuRepository.flush();
		
		//WHEN
		menuRepository.delete(menu);
		menuRepository.flush();
	}

}
