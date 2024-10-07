package partida;

import java.util.ArrayList;

import monopoly.Casilla;
import monopoly.Valor;


public class Jugador {

    //Atributos:
    private String nombre; //Nombre del jugador
    private Avatar avatar; //Avatar que tiene en la partida.
    private float fortuna; //Dinero que posee.
    private float gastos; //Gastos realizados a lo largo del juego.
    private boolean enCarcel; //Será true si el jugador está en la carcel
    private int tiradasCarcel; //Cuando está en la carcel, contará las tiradas sin éxito que ha hecho allí para intentar salir (se usa para limitar el numero de intentos).
    private int vueltas; //Cuenta las vueltas dadas al tablero.
    private ArrayList<Casilla> propiedades; //Propiedades que posee el jugador.

    //Constructor vacío para la banca
    public Jugador() {
        this.nombre="Banca";
        this.avatar=null;
        this.fortuna=Valor.FORTUNA_BANCA;
        this.gastos=0;
        this.enCarcel=false;
        this.tiradasCarcel=0;
        this.vueltas=0;
        this.propiedades=null;
    }

    /*Constructor principal. Requiere parámetros:
     * Nombre del jugador, tipo del avatar que tendrá, casilla en la que empezará y ArrayList de
     * avatares creados (usado para dos propósitos: evitar que dos jugadores tengan el mismo nombre y
     * que dos avatares tengan mismo ID). Desde este constructor también se crea el avatar.
     */
    public Jugador(String nombre, String tipoAvatar, Casilla inicio, ArrayList<Avatar> avCreados) {
        if ((nombre != null) && (tipoAvatar != null) ) {
            this.nombre = nombre;
            this.fortuna = Valor.FORTUNA_INICIAL;  // Initial fortune from Valor
            this.gastos = 0;
            this.enCarcel = false;
            this.tiradasCarcel = 0;
            this.vueltas = 0;
            this.propiedades = new ArrayList<>();
            this.avatar = new Avatar(tipoAvatar, this, inicio, avCreados);
        } else {
            System.out.println("Error al crear el jugaodr.\n");
        }
    }



    //Otros métodos:
    //Método para añadir una propiedad al jugador. Como parámetro, la casilla a añadir.

    public void anhadirPropiedad(Casilla casilla) {
        if (!propiedades.contains(casilla))
            this.propiedades.add(casilla);
    }


    //Método para eliminar una propiedad del arraylist de propiedades de jugador.
    public void eliminarPropiedad(Casilla casilla) {
        if (propiedades.contains(casilla))
            this.propiedades.remove(casilla);

    }

    //Método para añadir fortuna a un jugador
    //Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se pasaría un valor negativo.
    public void sumarFortuna(float valor) {
        this.fortuna += valor;
    }

    //Método para sumar gastos a un jugador.
    //Parámetro: valor a añadir a los gastos del jugador (será el precio de un solar, impuestos pagados...).
    public void sumarGastos(float valor) {
        this.gastos += valor;
    }

    /*Método para establecer al jugador en la cárcel.
     * Se requiere disponer de las casillas del tablero para ello (por eso se pasan como parámetro).*/
    public void encarcelar(ArrayList<ArrayList<Casilla>> pos) {
        for (ArrayList<Casilla> fila : pos) {
            for (Casilla casilla : fila) {
                if (casilla.getNombre().equals("Carcel")) {  // Buscar la casilla de cárcel por nombre
                    this.enCarcel = true;                    // Marcar al jugador como en cárcel
                    this.avatar.setLugar(casilla);           // Establecer directamente la casilla de cárcel como su nueva ubicación
                    this.tiradasCarcel = 0;                  // Reiniciar contador de tiradas en cárcel
                    System.out.println(this.nombre + " ha sido encarcelado.");
                    return;
                }
            }
        }
    }
    public void sumarVuelta(){vueltas++;}

    //esto aun no esta pero hace falta
    public boolean estaHipotecada() {return true;}


    public boolean estaEnBancarrota() {

        if (this.getFortuna() <= 0) {
            // Comprobar si tiene propiedades hipotecables
            for (Casilla propiedad : this.getPropiedades()) {
                if (!propiedad.estaHipotecada()) {
                    // Si el jugador tiene al menos una propiedad sin hipotecar, no está en bancarrota
                    return false;
                }
            }
            // Si llega aquí, significa que no tiene dinero ni propiedades útiles
            return true;
        }
        // Si tiene fortuna, no está en bancarrota
        return false;
    }

    //GETTERS Y SETTERS
    public String getNombre(){
        return this.nombre;
    }


    public float getFortuna(){
        return this.fortuna;
    }



    public Avatar getAvatar() {return avatar;}

    public float getGastos() {return gastos;}

    public void setGastos(float gastos) {this.gastos = gastos;}

    public boolean isEnCarcel() {return enCarcel;}

    public void setEnCarcel(boolean enCarcel) {this.enCarcel = enCarcel;}

    public void setTiradasCarcel(int tiradasCarcel) {this.tiradasCarcel = tiradasCarcel;}

    public int getVueltas() {return vueltas;}

    public void setVueltas(int vueltas) {this.vueltas = vueltas;}

    public ArrayList<Casilla> getPropiedades() {return propiedades;}





    public void infoJugador() {
        // Imprimir nombre, avatar y fortuna
        System.out.println("nombre: " + this.getNombre() + ",");
        System.out.println("avatar: " + this.getAvatar().getId() + ",");
        System.out.println("fortuna: " + this.getFortuna() + ",");

        // Imprimir propiedades
        System.out.print("propiedades: ");
        if (this.getPropiedades().isEmpty()) {
            System.out.println("Ninguna");
        } else {
            System.out.print("[");
            for (int i = 0; i < this.getPropiedades().size(); i++) {
                System.out.print(this.getPropiedades().get(i).getNombre());
                if (i < this.getPropiedades().size() - 1) {
                    System.out.print(", ");
                    //añade coma a todo menos a la ulltima por eso el menos 1
                }
            }
            System.out.println("]");
        }
    }
}
