# TestJava
Working with XMl files

Aceasta aplicatie are ca scop preluarea de comenzi si transmiterea acestora mai departe catre furnizori.
Pentru dezvoltarea si testarea acestei aplicatii s-au folosit doua fisiere XML,unul este o copie a exemplului primit in documentatia pentru acest test, iar celalalt este o copie usor modeificata a acestuia.
Mod de functionare:
Pasul1: 
In momentul in care se apasa butonul “Run” aplicatia cauta in folderul sursa daca exista fisiere de tipul XML, dupa care fisierele XML gasite sunt salvate intr-o lista
In cazul meu calea catre folderul sursa este : "D:\\TestFiles"
Pasul2: 
Urmatorul pas este citirea pe rand a fiecarui fisier salvat in lista.
Din acestea se extrag informatiile despre fiecare produs comandat si se salveaza intr-un obiect, dupa care fiecare obiect este salvat intr-o lista.
Pasul3: 
Se salveaza fiecare producator intr-un HashSet.
Pasul4:
Pentru fiecare producator se creeaza un nou fisier XML si produsele comandate sunt selectate  din lista de obiecte dupa care detaliile despre acestea sunt scrise in fisierul nou creat.
Pasul5:
Fisierele XML create pentru fiecare producator sunt afisate atat in consola cat si create in folderul destinatie.
In cazul meu folderul destinatie este: "D:\\ResultTestFiles"

