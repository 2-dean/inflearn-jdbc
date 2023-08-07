package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;


/**
 * JDBCTemplate 사용
 */
@Slf4j
public class MemberRepositoryV5 implements MemberRepository{


    private final JdbcTemplate template;

    public MemberRepositoryV5(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member(member_id, money) values (?, ?)";
        int update = template.update(sql, member.getMemberId(), member.getMoney());
        return member;
    }

    @Override
    public Member findById(String memberId) {
        String sql = "select * from member where member_id = ?";
        return template.queryForObject(sql, memberRowMapper(), memberId);
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setMemberId(rs.getString("member_id"));
            member.setMoney(rs.getInt("money"));
            return member;
        };
    }

    @Override
    public void update(String memberId, int money) {
        String sql = "update member set money=? where member_id=?";
        template.update(sql, money, memberId);
    }

    @Override
    public void delete(String memberId) {
        String sql = "delete from member where member_id=?";
        template.update(sql, memberId);

    }


//    private void close (Connection con, Statement stmt, ResultSet rs) {
//        JdbcUtils.closeResultSet(rs);
//        JdbcUtils.closeStatement(stmt);
//        // 주의! 트랜잭션 동기화를 사용하려면 DataSourceUtils를 사용해야함
//        // connection.close() -> 커넥션 닫기
//
//        //트랜잭션 닫기, 커넥션은 그대로 유지해줌 트랜잭션 동기화매닞너가 관리하는 커넥션이 없을때 해당 커넥션을 닫음
//        DataSourceUtils.releaseConnection(con, dataSource);
//    }
//
//    private Connection getConnection() throws SQLException {
//        // 주의! 트랙잭션 동기화를 사용하려면 DataSourceUtils 를 사용해야 한다.
//        Connection con = DataSourceUtils.getConnection(dataSource); // 커넥션이 있으면 있는거 쓰고 없으면 새로운 커낵션 생성해서반환
//        log.info("getConnerction={}, class={}", con, con.getClass());
//        return con;
//    }

}
