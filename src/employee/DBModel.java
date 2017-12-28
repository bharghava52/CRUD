package employee;

public interface DBModel {

  public int create(String name,String mail);
  public int update(String id);
  public int delete(String id);
}
