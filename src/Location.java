import java.util.ArrayList;
import java.util.List;
import java.time.temporal.ChronoUnit;
import java.util.Date;

// constructorul pentru locatie

public class Location
{
    public String nume, numeTara, numeOras;
    public double pretMediuZi;
    public Date dataInceput, dataSfarsit;
    // variabilele care vor fi citite pentru fiecare locatie


    List<String> listaActivitati = new ArrayList<String>();

    public void addActivity(String numeActivitate)
    {
        listaActivitati.add(numeActivitate);
    }
    // adaugarea listei de activitati

    public double pretTotal10Zile()
    {
        return (double) (10 * pretMediuZi);
    }
    // o funtctie care intoarce pretul mediu pe 10 zile

    public double pretPentruSejur(Date dataInceput, Date dataSfarsit)
    {
        return (double) (pretMediuZi *
                ChronoUnit.DAYS.between(dataInceput.toInstant(),
                        dataSfarsit.toInstant()));
    }
    // pret mediu pentru un sejur

    public Location(String requiredName, String requiredCountry,
                    String requiredCity, double requiredPrice,
                    Date requiredBeginingDate, Date requiredEndingDate)
    {
        nume = requiredName;
        numeTara = requiredCountry;
        numeOras = requiredCity;
        pretMediuZi = requiredPrice;
        dataInceput = requiredBeginingDate;
        dataSfarsit = requiredEndingDate;
    }
    // constructorul

    @Override
    public String toString()
    {
        String activitatiPosibile = "";
        for (String activitatePosibila : listaActivitati)
        {
            activitatiPosibile += activitatePosibila;
            activitatiPosibile += " ";
        }

        return nume + " " + numeTara + " " + numeOras + " " + pretMediuZi + " " +
                dataInceput + " " + dataSfarsit + " " + activitatiPosibile;
    }
}
