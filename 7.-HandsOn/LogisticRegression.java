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

import java.util.function.DoubleToLongFunction;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

/**
   This example shows a minimal agent that just prints "Hallo World!" 
   and then terminates.
   @author Giovanni Caire - TILAB
 */
public class LogisticRegression extends Agent {
  double w[] = new double[3];
  double x[] = new double[3];
  double y[] = new double[3];
  double z[] = new double[3];
  double r[] = new double[3];

  double alpha = 0.1;


  protected void setup() {
    // Say the agent name
  	System.out.println("Hello World! My name is "+getLocalName());
    fill();
    // Eject the agent
  	addBehaviour(new MyOneShot());
  } 

  double myX = 1;
  double myY = 3.5;
  double myZ = 4;

  private void fill(){
    w[0] = 0;
    w[1] = 0;
    w[2] = 0;

    x[0] = 1;
    x[1] = 1;
    x[2] = 1;

    y[0] = 1;
    y[1] = 4;
    y[2] = 2;

    z[0] = 1;
    z[1] = 2;
    z[2] = 4;

    r[0] = 0;
    r[1] = 1;
    r[2] = 1;
  }

  // This is the one shot behavior
  public class MyOneShot extends OneShotBehaviour {
    public void action(){
      for(int i = 0; i < 100; i++){
        w[0] = getW(0, x);
        // System.out.println("W["+ 0 + "]= "+ w[1]);
        w[1] = getW(1, y);
        w[2] = getW(2, z);
      }
      System.out.println("--------------------------");
      for(int i = 0; i < 3; i++)
        System.out.println("W["+ i + "]= "+ w[i]);


      // Get the result
      double operation = ((w[0]*myX) + (w[1]*myY) + (w[2]*myZ));
      double result = 1/(1 + Math.exp(-operation));

      System.out.println("Range: "+ result);
    }

    public double getW(int location, double[] arr){
      double sum = 0;
      for (int i = 0; i < 3; i++)
        sum += (r[i] - 0.5) * arr[i];
      return w[location] - (alpha * sum);
    }


    public int onEnd(){
      System.out.println("Im done good bye world");
      myAgent.doDelete();
      return super.onEnd();
    }
  }
}

