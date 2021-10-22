/**
 * ***************************************************************
 * JADE - Java Agent DEvelopment Framework is a framework to develop
 * multi-agent systems in compliance with the FIPA specifications.
 * Copyright (C) 2000 CSELT S.p.A.
 * 
 * GNU Lesser General Public License
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation,
 * version 2.1 of the License.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307, USA.
 * **************************************************************
 */
package examples.hello;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import java.util.Scanner;
/**
   This example shows a minimal agent that just prints "Hallo World!" 
   and then terminates.
   @author Giovanni Caire - TILAB
 */
public class OptimizingPredictive extends Agent {
  // int x[] = new int[9];
  java.util.Scanner reader = new Scanner(System.in);
  // float x[] = {23, 26, 30, 34, 43, 48, 52, 57, 58};
  // float y[] = {651, 762, 856, 1063, 1190, 1298, 1421, 1440, 1528};
  float x[] = new float[100];
  float y[] = new float[100];
  float error[] = new float[100];
  float resultado, b0, b1, n;

  protected void setup() {
  	System.out.println("Hello World! My name is "+getLocalName());

    x[0] = 23;
    x[1] = 26;
    x[2] = 30;
    x[3] = 34;
    x[4] = 43;
    x[5] = 48;
    x[6] = 52;
    x[7] = 57;
    x[8] = 58;

    y[0] = 651;
    y[1] = 762;
    y[2] = 856;
    y[3] = 1063;
    y[4] = 1190;
    y[5] = 1298;
    y[6] = 1421;
    y[7] = 1421;
    y[8] = 1528;

    n = 8;

    addBehaviour(new MyOneShot());
    // for(int i = 0; i < n; i++){
    //   System.out.println("("+x[i]+", "+y[i]+")");
    // }
  } 

  public class MyOneShot extends OneShotBehaviour {
    public void action(){
      // System.out.println("Cuantos valores ingresara");
      // n = reader.nextFloat();

      // for(int i = 0; i < n; i++){
      //   System.out.println("Ingrese el valor x["+i+"]");
      //   x[i] = reader.nextFloat();
      //   System.out.println("Ingrese el valor Y["+i+"]");
      //   y[i] = reader.nextFloat();
      // }

      b0 = rb0();
      b1 = rb1();
      System.out.println("B0="+b0+ " B1="+b1);

      float alpha = 0.0030f;
      int n_iter = 40;
      // minimize E function

      float lasterror = findError();
      float newError = lasterror;
      
      for (int i = 0; i < n_iter || newError < lasterror; i++){
        lasterror = newError;
        float b0new = b0 - alpha * derivateB0();
        b1 = b1 - alpha * derivateB1();
        b0 = b0new;
        newError = findError();
      }

      // preditcion();
    }

    public int onEnd() {
      System.out.println("Im done good bye world");
      myAgent.doDelete();
      return super.onEnd();
    }
  }
  
  // loss regresion
    protected float findError(){
      float errorcito = 0;
      // obtener el error de cada uno
      for (int i = 0; i< n; i++){
        errorcito += (y[i]-(b0 + b1*x[i])) * (y[i]-(b0 + b1*x[i]));
      }
      errorcito = errorcito / n;
      System.out.println("el errorcito es: "+errorcito);
      return errorcito;
    }

    protected float derivateB0(){
      float sum = 0;
      for(int i = 0; i< n; i++){
        sum += y[i] - (b0 + b1*x[i]);
      }
      sum = (-2*sum)/n;
      return (sum);
    }

    protected float derivateB1(){
      float sum = 0;
      for(int i = 0; i< n; i++){
        sum += x[i]*(y[i]-(b0 + b1*x[i]));
      }
      sum = (-2*sum)/n;
      return (sum);
    }
  // end

  protected float preditcion(){
    System.out.println("Ingrese el valor a predecir:");
    float average = reader.nextFloat();
    resultado = b0+b1*average;

    System.out.println("Prediccion ("+average+") = "+resultado);
    return (resultado);
  }

  protected float sumatoria(float[] arr){
    float semiresultado = 0;
    for (int i = 0; i < n; i++)
      semiresultado += arr[i];
    return semiresultado;
  }

  protected float sumatoriaDos(float[] arr, float[] arr2){
    float semiresultado = 0;
    for (int i = 0; i < n; i++)
      semiresultado += arr[i] * arr2[i];
    return semiresultado;
  }


  protected float d(){
    float izquierda = n * sumatoriaDos(x, x);
    float derecha = sumatoria(x) * sumatoria(x);
    return (izquierda - derecha);
  }

  protected float db1(){
    float izquierda = n * sumatoriaDos(x, y);
    float derecha = sumatoria(x) * sumatoria(y);
    return (izquierda - derecha);
  }

  protected float rb1(){
    return (db1()/d());
  }

  protected float rb0(){
    float promedioX = sumatoria(x) / n;
    float promedioY = sumatoria(y) / n;
    return (promedioY - (rb1() * promedioX));
  }
}

