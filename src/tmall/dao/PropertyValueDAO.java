package tmall.dao;

import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropertyValueDAO {

    public int getTotal() {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {

            String sql = "select count(*) from PropertyValue";

            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return total;
    }

    public void add(PropertyValue bean) {

        String sql = "insert into Property values(null,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, bean.getProduct().getId());
            ps.setInt(2, bean.getProperty().getId());
            ps.setString(3, bean.getValue());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                bean.setId(id);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void update(PropertyValue bean) {


        String sql = "update Property set pid = ?, ptid = ?, value = ? where id = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, bean.getProduct().getId());
            ps.setInt(2, bean.getProperty().getId());
            ps.setString(3, bean.getValue());
            ps.setInt(4, bean.getId());

            ps.execute();

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public void delete(int id) {

        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {

            String sql = "delete from PropertyValue where id = " + id;

            s.execute(sql);

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public PropertyValue get(int id) {
        PropertyValue bean = new PropertyValue();

        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {

            String sql = "select * from PropertyValue where id = " + id;

            ResultSet rs = s.executeQuery(sql);

            if (rs.next()) {

                int ptid = rs.getInt("ptid");
                Property property = new PropertyDAO().get(ptid);
                int pid = rs.getInt("pid");
                Product product = new ProductDAO().get(pid);
                String value = rs.getString("value");

                bean.setId(id);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return bean;
    }

    public PropertyValue get(int ptid, int pid) {
        PropertyValue bean = null;

        String sql = "select * from PropertyValue where ptid = ? and pid = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, ptid);
            ps.setInt(2, pid);

            ResultSet rs = ps.executeQuery(sql);

            if (rs.next()) {
                bean = new PropertyValue();

                int id = rs.getInt("id");
                Product product = new ProductDAO().get(pid);
                Property property = new PropertyDAO().get(ptid);
                String value = rs.getString("value");

                bean.setId(id);
                bean.setValue(value);
                bean.setProperty(property);
                bean.setProduct(product);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return bean;
    }

    public List<PropertyValue> list() {
        return list(0, Short.MAX_VALUE);
    }

    public List<PropertyValue> list(int start, int count) {
        List<PropertyValue> beans = new ArrayList<PropertyValue>();

        String sql = "select * from PropertyValue order by id desc limit ?,? ";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, start);
            ps.setInt(2, count);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PropertyValue bean = new PropertyValue();
                int id = rs.getInt(1);

                int pid = rs.getInt("pid");
                int ptid = rs.getInt("ptid");
                String value = rs.getString("value");

                Product product = new ProductDAO().get(pid);
                Property property = new PropertyDAO().get(ptid);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setId(id);
                beans.add(bean);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return beans;
    }


    //    初始化某个产品对应的属性值，初始化逻辑：
//  1. 根据分类获取所有的属性
//  2. 遍历每一个属性
//  2.1 根据属性和产品，获取属性值
//  2.2 如果属性值不存在，就创建一个属性值对象
//
    public void init(Product p) {

        int pid = p.getId();

        List<Property> pts = new PropertyDAO().list(p.getCategory().getId());

        for (Property pt : pts) {

            PropertyValue pv = get(pt.getId(), p.getId());
            if (null == pv) {
                pv = new PropertyValue();
                pv.setProduct(p);
                pv.setProperty(pt);
                this.add(pv);
            }

        }

    }

    public List<PropertyValue> list(int pid) {
        List<PropertyValue> beans = new ArrayList<PropertyValue>();

        String sql = "select * from PropertyValue where pid = ? order by ptid desc";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, pid);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PropertyValue bean = new PropertyValue();
                int id = rs.getInt(1);

                int ptid = rs.getInt("ptid");
                String value = rs.getString("value");

                Product product = new ProductDAO().get(pid);
                Property property = new PropertyDAO().get(ptid);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setId(id);
                beans.add(bean);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return beans;
    }

}