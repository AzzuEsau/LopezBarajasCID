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

import java.util.Random;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
/**
   This example shows a minimal agent that just prints "Hallo World!" 
   and then terminates.
   @author Giovanni Caire - TILAB
 */
public class GradientDescendent extends Agent {

  protected void setup() {
  	System.out.println("Hello World! My name is "+getLocalName());
    addBehaviour(new MyOneShot());
  } 

  // javac -cp lib/jade.jar src/examples/hello/GradientDescendent.java -d classes
  // java -cp lib/jade.jar:classes/ jade.Boot -gui gradient:examples.hello.GradientDescendent

  public class MyOneShot extends OneShotBehaviour {
    // Numers of iteration
    float n_iter = 40;
    // Learning
    float alpha = .025f;

    public void action() {
      float gradient;
      float solution = -0.70f;
      for (int i = 0; i < n_iter; i++){
        gradient = derivate(solution);
        solution = solution - alpha * gradient;
        float y = objetive(solution);
        System.out.println(i+ ".- F(["+solution+"]) = "+y);
      }
    }
    
    public int onEnd() {
      System.out.println("Im done good bye world");
      myAgent.doDelete();
      return super.onEnd();
    }

    // Derivate
    public float derivate(float x){
      return 2*x;
    }

    // Solution of the f(x)
    public float objetive(float x){
      return x * x;
    }
  }

    // public class MyOneShot extends OneShotBehaviour {
    //   public void action() {
    //     // El jemplo de la gradiente es de f(x) = x^2
    //     // Donde empieza x es un valor random
    //     float x_initial = -10;
    //     float aplha = 0.25;
    //     int itearions = 100;
        
    //     // Iniciamos la operacion del gradiente decendiente
    //     float x = x_initial;
    //     for (int i = 0; i <= itearions; i++)
    //     {
    //       System.out.println("-------------------------------------------");
    //       System.out.println("Iteracion: ", i);
    //       int gradient = 2*x;
    //       // Acutalizar x usando gradiente
    //       x = x - aplha * gradient;
    //       // Damos la solucion resolviendo la relacion
    //       System.out.println("X = ", x, ", Y = ", x*x);
    //     }
    //   }
      
    //   public int onEnd() {
    //     myAgent.doDelete();
    //     return super.onEnd();
    //   }
    // }
}

