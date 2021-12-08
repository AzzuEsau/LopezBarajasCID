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

import java.time.Year;
import java.util.*;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

/**
   This example shows a minimal agent that just prints "Hallo World!" 
   and then terminates.
   @author Giovanni Caire - TILAB
 */
public class KNN extends Agent {
  double height[] = new double[11];
  double age[] = new double[11];
  double weight[] = new double[11];

  double[][] nearest = new double[10][2];
  int k = 3, n = 10;


  protected void setup() {
    // Say the agent name
  	System.out.println("Hello World! My name is "+getLocalName());
    fillDataset();
    // Eject the agent
  	addBehaviour(new MyOneShot());
  } 

  private void fillDataset(){
    height[0] = 5;
    age[0]    = 45;
    weight[0] = 77;
    
    height[1] = 5.11f;
    age[1]    = 26;
    weight[1] = 47;

    height[2] = 5.6f;
    age[2]    = 30;
    weight[2] = 55;

    height[3] = 5.9f;
    age[3]    = 34;
    weight[3] = 59;

    height[4] = 4.8f;
    age[4]    = 40;
    weight[4] = 72;

    height[5] = 5.8f;
    age[5]    = 36;
    weight[5] = 60;

    height[6] = 5.3f;
    age[6]    = 19;
    weight[6] = 40;

    height[7] = 5.8f;
    age[7]    = 28;
    weight[7] = 60;

    height[8] = 5.5f;
    age[8]    = 23;
    weight[8] = 45;

    height[9] = 5.6f;
    age[9]    = 32;
    weight[9] = 58;

    height[10] = 5.5f;
    age[10]    = 38;
    weight[10] = 0;
  }


  // This is the one shot behavior
  public class MyOneShot extends OneShotBehaviour {
    
    public void action(){
      // Get all the distances
      for(int x = 0; x < n; x++)
      {
        nearest[x][0] = getDistance(x, n);
        nearest[x][1] = x;
      }

      System.out.println("-------------------------------------------");

      // Sort the distances
      Arrays.sort(nearest, Comparator.comparingDouble(o-> o[0]));
      for(int x = 0; x < n; x++)
        System.out.println("["+(int)nearest[x][1]+"] mt=" + nearest[x][0] + " kg= " + weight[(int)nearest[x][1]]);

      // Get the average
      double avg = 0;
      for (int x = 0; x < k; x++)
      {
        avg += weight[(int)nearest[x][1]];
      }
      avg = avg / k;

      System.out.println("Peso = " + avg + "Kg");
    }

    public double getDistance(int a, int b){
      double distance = (double)Math.pow(height[a]-height[b], 2) + (double)Math.pow(age[a]-age[b], 2);
      return (double)Math.sqrt(distance);
    }

    public int onEnd(){
      System.out.println("Im done good bye world");
      myAgent.doDelete();
      return super.onEnd();
    }
  }
}

