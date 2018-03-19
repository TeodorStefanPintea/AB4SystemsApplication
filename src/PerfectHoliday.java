import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


// programul principal care o sa fie apelat de catre utilizator
public class PerfectHoliday
{
    private static
    Map<String, Location> LocationMap = new HashMap<String, Location>();
    // voi folosi un hash map pentru a eficientiza cautarea unei anumite locatii
    private static
    Map<String, ArrayList<Location>> CountryNameMap = new HashMap<>();
    // voi folosi cate un hash map pentru fiecare ierarhie a unei locatii

    private static
    Map<String, Location> CityNameMap = new HashMap<String, Location>();

    private static List<Location> LocationList = new ArrayList<Location>();
    // un array list folosit in functie de cerionta utilizatorului

    private static int cerintaUtilizator;


    public static Date formareData(String requiredString)
    {
        String [] arrayOfDates = requiredString.split("/");
        int year = Integer.parseInt(arrayOfDates[2]);
        int month = Integer.parseInt(arrayOfDates[1]);
        int day = Integer.parseInt(arrayOfDates[0]);

        return new Date(year - 1900 + 2000, month, day);
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        File input = new File("information.in");

        Scanner inputFile = new Scanner(input);
        // fisierul care se va apela pentru a introduce o lista de locatii

        Scanner inputLine = new Scanner(System.in);
        // scanner care va prelua a doua comanda a utilizatorului

        String currentLine;
        String [] arrayDataIntrare;

        while (inputFile.hasNextLine()) {
            currentLine = inputFile.nextLine();
            arrayDataIntrare = currentLine.split(" ");

            if (arrayDataIntrare != null) {
                // parsez datele primite ca parametrii


                Location locatie = new Location(arrayDataIntrare[0],
                        arrayDataIntrare[1],
                        arrayDataIntrare[2],
                        Double.parseDouble(arrayDataIntrare[3]),
                        formareData(arrayDataIntrare[4]),
                        formareData(arrayDataIntrare[5]));

                for (int index = 6; index < arrayDataIntrare.length; index++)
                    locatie.addActivity(arrayDataIntrare[index]);

                LocationList.add(locatie);
                LocationMap.put(locatie.nume, locatie);
                //CountryNameMap.put(locatie.numeTara, locatie);
                if (!CountryNameMap.containsKey(locatie.numeTara))
                    CountryNameMap.put(locatie.numeTara, new ArrayList<Location>());
                CountryNameMap.get(locatie.numeTara).add(locatie);

                CityNameMap.put(locatie.numeOras, locatie);
                // pentru a extinde ierarhizarea, se pot folosi hash mapuri multiple
                // pentru a retine informatiile despre o anumita locatie

        /* adaugarea obiectelor atat in array list, cat si in HashMap este utila
        deoarece depinzand de cerinta utilizatorului, se pot folosi
        alternativ */
            } // if
            else break;
        }
            System.out.println("Ce doriti sa aflati?\n" +
                    "1 : Toate informatiile despre o anumita locatie. \n" +
                    "2 : Cele mai bune oferte dintr-o zona geografica.\n" +
                    "3 : Unde este cel mai ieftin sa practicati o anumita activitate.\n");
            cerintaUtilizator = inputLine.nextInt();
      /* aceasta variabila retine comanda pe care o vrea utilizatorul
      1 reprezinta toata informatia despre o anumita locatie X
      2 top 5 locatii din tara, judetul, orasul, etc
      3 cel mai ieftin sa practic o anumita activitate 10 zile */

            Comparator<Location> defaultComparator =
                    new Comparator<Location>()
                    {
                        // un comparator care o sa fie folosit pentru a sorta obiectele in
                        // functie de pretul lor
                        public int compare(Location location1, Location location2)
                        {
                            return (int)(location1.pretTotal10Zile()
                                    - location2.pretTotal10Zile());
                        }
                        // comparam in functioe de pretul lor total
                    };


            Comparator<Location> totalPriceComparator =
                    new Comparator<Location>() {
                        @Override
                        public int compare(Location location1, Location location2) {
                            return (int)(location2.pretPentruSejur(location2.dataInceput, location2.dataSfarsit)
                                    - location1.pretPentruSejur(location1.dataInceput, location1.dataSfarsit));
                        }
                    };

            if ( cerintaUtilizator == 1)
            {
                System.out.println("Ce locatie doriti sa cautati?");
                String cautaLocatie = inputLine.next();
                Location location = LocationMap.get(cautaLocatie);

                if (location == null)
                    System.out.println("Nu exista un obiect cu numele " + cautaLocatie);
                else
                    System.out.println(location);
            }
            else
            if (cerintaUtilizator == 2) {
                System.out.println("In ce tara doriti sa cautati oferte?");
                String cautaLocatie = inputLine.next();
                if (!CountryNameMap.containsKey(cautaLocatie))
                    System.out.println("Nu exista locatie cu acest nume.");
                else {
                    List<Location> listaLocatiiDinTara = CountryNameMap.get(cautaLocatie);
                    Collections.sort(listaLocatiiDinTara, (location1, location2) -> {
                        return (int) (location1.pretPentruSejur(location1.dataInceput, location1.dataSfarsit) -
                                location2.pretPentruSejur(location2.dataInceput, location2.dataSfarsit));
                    });
                    int contor = 0;
                    for (int index = 0; index < listaLocatiiDinTara.size(); index++) {
                        contor++;
                        if (contor <= 5) {
                            System.out.println(listaLocatiiDinTara.get(index));
                        } else break;
                    }

                }
            }
            else
                if (cerintaUtilizator == 3) {
                    System.out.println("Cea mai ieftina locatie pentru a practica o " +
                            "anumita activitate pentru 10 zile este: ");
                    Collections.sort(LocationList, defaultComparator);
                    double pretMinim = LocationList.get(0).pretTotal10Zile();
                    for(int index = 0; index < LocationList.size(); index++)
                        if(pretMinim == LocationList.get(index).pretTotal10Zile())
                            System.out.println(LocationList.get(index));
                    // sortarea dupa pretul mediu pe 10 zile ale locatiilor si afisarea celor cu

                }
    }
}
