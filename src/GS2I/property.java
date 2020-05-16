package GS2I;


import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class property {
	private StringProperty id;
    private StringProperty company;
    private StringProperty brand;
    private StringProperty problem;
    private StringProperty Serial_Number;
    private StringProperty Diagnotics;
    private ObjectProperty<LocalDate>Return_Date;
    private ObjectProperty<LocalDate>Entry_Date;
	public property(String id,LocalDate Entry_Date, String company, String brand,String Serial_Number, String problem,LocalDate Return_Date, String Diagnotics) {
		
		this.id = new SimpleStringProperty (id);
		this.company = new SimpleStringProperty(company);
		this.brand = new SimpleStringProperty(brand);
		this.problem = new SimpleStringProperty(problem);
		this.Serial_Number= new SimpleStringProperty(Serial_Number);
		this.Diagnotics =new SimpleStringProperty(Diagnotics);
		this.Return_Date = new SimpleObjectProperty<LocalDate>(Return_Date);
		this.Entry_Date =new SimpleObjectProperty<LocalDate>(Entry_Date);
	}
	
	public void setId(String id)
	{
		this.id=new SimpleStringProperty (id);
	}
    
	public String getId()
	{
		return id.get();
	}
	public void setCompany(String company)
	{
		 this.company=new SimpleStringProperty(company);
	}
	public String getCompany() {
		return company.get();
	}
	public String getSerial_Number() {
		return Serial_Number.get();
	}
	public void setSerial_Number(String Serial_Number)
	{
		this.Serial_Number=new SimpleStringProperty(Serial_Number);
	}
	public String getBrand() {
		return brand.get();
	}
	public void setBrand(String brand)
	{
		this.brand=new SimpleStringProperty(brand);
	}
	public void setProblem(String problem)
	{
		this.problem=new SimpleStringProperty(problem);
	}
	public String getProblem()
	{
		return problem.get();
	}
	
	public void setDiagnotics(String Diagnotics)	{
		this.Diagnotics=new SimpleStringProperty(Diagnotics);
	}
	public String getDiagnotics()
	{
		return Diagnotics.get();
	}
	public LocalDate getReturnDate() {
	    return Return_Date.get();
	}
	public void setReturnDate(LocalDate Return_Date) {
	    this.Return_Date = new SimpleObjectProperty<>(Return_Date);
	}
	public LocalDate getEntryDate() {
	    return Entry_Date.get();
	}
	public void setEntryDate(LocalDate Entry_Date) {
	    this.Entry_Date = new SimpleObjectProperty<>(Entry_Date);
	}
	public ObjectProperty<LocalDate> entryDateProperty() {
	    return Entry_Date;
	}
	public ObjectProperty<LocalDate> returnDateProperty() {
	    return Return_Date;
	}
}
