import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;

class Main {
    public static void main(String[] args) throws SQLException {
        read();
        // create();
        // update();
        // delete();
    }



    
    public static void read() throws SQLException {
        String url = "jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:6543/postgres?user=postgres.bvdonywsxjzmlhxdcixv&password=87iwpcTT355oTDt3";

        Connection c = DriverManager.getConnection(url);
        String sql = "SELECT * FROM contas";
        PreparedStatement stm = c.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            long nro = rs.getLong("nro_conta");
            BigDecimal saldo = rs.getBigDecimal("saldo");

            System.out.println("Conta numero: " + nro + " tem saldo de R$ " + saldo);
        }

        c.close();
    }

    public static void create() throws SQLException {
        System.out.print("Numero para a nova conta: ");
        long nro = Long.parseLong(System.console().readLine());
        System.out.print("Saldo da nova conta: ");
        BigDecimal saldo = new BigDecimal(System.console().readLine());

        String url = "jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:6543/postgres?user=postgres.bvdonywsxjzmlhxdcixv&password=87iwpcTT355oTDt3";
        
        Connection c = DriverManager.getConnection(url);

        String sql = "INSERT into contas VALUES (?, ?)";
        PreparedStatement prepstm = c.prepareStatement(sql);
        prepstm.setLong(1, nro);
        prepstm.setBigDecimal(2, saldo);

        int ret = prepstm.executeUpdate();
        System.out.println("Numero de registros inseridos: " + ret);

        prepstm.close();
        c.close();
    }
    
    
    public static void update() throws SQLException {
    System.out.print("Numero de uma conta ja existente: ");
    long nro = Long.parseLong(System.console().readLine());
    System.out.print("Novo saldo para esta conta: ");
    BigDecimal saldo = new BigDecimal(System.console().readLine());

    String url = "jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:6543/postgres?user=postgres.bvdonywsxjzmlhxdcixv&password=87iwpcTT355oTDt3";
    Connection c = DriverManager.getConnection(url);

    String sql = "UPDATE contas SET saldo=? WHERE nro_conta=?";
    PreparedStatement prepstm = c.prepareStatement(sql);
    prepstm.setBigDecimal(1, saldo);
    prepstm.setLong(2, nro);
    
    int ret = prepstm.executeUpdate();
    System.out.println("Numero de registros alterados: " + ret);
    
    c.close();
    }

    public static void delete() throws SQLException {
    System.out.print("Numero de uma conta ja existente: ");
    long nro = Long.parseLong(System.console().readLine());
    String url = "jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:6543/postgres?user=postgres.bvdonywsxjzmlhxdcixv&password=87iwpcTT355oTDt3";
    Connection c = DriverManager.getConnection(url);
    String sql = "DELETE FROM contas WHERE nro_conta=?";
    PreparedStatement prepstm = c.prepareStatement(sql);
    prepstm.setLong(1, nro);

    int ret = prepstm.executeUpdate();
    System.out.println("Numero de registros apagados: " + ret);

    c.close();
}
}
