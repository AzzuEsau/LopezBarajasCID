import java.util.Scanner;

public class Main{
  public static void main(String[] args){
    java.util.Scanner reader = new Scanner(System.in);
    float x[] = new float[100];
    float y[] = new float[100];
    float n;


    System.out.println("Cuantos valores ingresara");
    n = reader.nextFloat();

    for(int i = 0; i < n; i++){
      System.out.println("Ingrese el valor x["+i+"]");
      x[i] = reader.nextFloat();
      System.out.println("Ingrese el valor Y["+i+"]");
      y[i] = reader.nextFloat();
    }

    float b1 = rb1(n, x, y);
    float b0 = rb0(n, b1, x, y);
    System.out.println("B0="+b0+ " B1="+b1);

    System.out.println("Ingrese el valor a predecir:");
    float average = reader.nextFloat();
    System.out.println("Prediccion ("+average+") = "+(b0+b1*average));

    reader.close();
    
  }


  public static float sumatoria(float n, float[] arr){
    float semiresultado = 0;
    for (int i = 0; i < n; i++)
      semiresultado += arr[i];
    return semiresultado;
  }

  public static float sumatoriaDos(float n, float[] arr, float[] arr2){
    float semiresultado = 0;
    for (int i = 0; i < n; i++)
      semiresultado += arr[i] * arr2[i];
    return semiresultado;
  }


  public static float d(float n, float[] x){
    float izquierda = n * sumatoriaDos(n, x, x);
    float derecha = sumatoria(n, x) * sumatoria(n, x);
    return (izquierda - derecha);
  }

  public static float db1(float n, float[] x, float[] y){
    float izquierda = n * sumatoriaDos(n, x, y);
    float derecha = sumatoria(n, x) * sumatoria(n, y);
    return (izquierda - derecha);
  }

  public static float rb1(float n, float[] x, float[] y){
    return (db1(n, x, y)/d(n, x));
  }

  public static float rb0( float n, float b1, float[] x, float[] y){
    float promedioX = sumatoria(n, x) / n;
    float promedioY = sumatoria(n, y) / n;
    return (promedioY - (b1 * promedioX));
  }
}