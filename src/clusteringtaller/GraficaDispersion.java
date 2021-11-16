/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clusteringtaller;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.editor.ChartEditorFactory;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYIntervalSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import weka.clusterers.SimpleKMeans;
import weka.core.Instance;
import weka.core.Instances;

public class GraficaDispersion {
    
    
    public GraficaDispersion(){
       
    }
    public ChartPanel generarGrafica(){
        
        XYDataset dataset=crearDataset();
        JFreeChart chart=ChartFactory.createScatterPlot("X vs Y","X", "Y", dataset);
        ChartPanel cp=new ChartPanel(chart);
        return cp; 
    }
    
    public XYDataset crearDataset(){
        
        XYSeriesCollection dataset=new XYSeriesCollection();
        XYSeries c1=new XYSeries("C1");
        XYSeries c2=new XYSeries("C2");
        XYSeries c3=new XYSeries("C3");
        
        try{
            
            String ruta="temperatura-Viento.arff";
            Instances datasetM=new Instances(new BufferedReader(new FileReader(ruta)));
            
            SimpleKMeans skm=new SimpleKMeans();
            skm.setNumClusters(3);
            skm.setPreserveInstancesOrder(true);
            skm.buildClusterer(datasetM);
            
            /***********MODELO DE CLUSTERING***************/
            System.out.println("Modelo: "+skm);
                              
            Instances centroides=skm.getClusterCentroids();                
            int asig[]=skm.getAssignments();
            int arr1[]=skm.getClusterSizes();
            System.out.println("Asignaciones "+Arrays.toString(asig));
            System.out.println("Instancias por cluster "+Arrays.toString(arr1));
            
            
            /*DISPERSION*/
            double x[] = {25.2,28.5,28.4,28.1,28.0,27.9,27.8,27.7,27.5,27.4,27.4,27.5,27.5,27.4,27.2,27.2,27.8,28.7,29.3,29.9,30.2,29.8,29.4,29.0,28.9,28.7,28.3,27.9,27.8,27.8,27.9,27.9,27.9,27.9,27.8,27.6,27.4,27.2,27.2,27.2,27.8,28.4,29.0,29.5,29.6,29.5,29.2,29.0,28.9,28.7,28.3,28.0,26.5,26.6,27.7,26.6,26.6,26.5,26.5,26.3,25.9,25.6,25.0,25.2,26.2,27.4,28.4,29.1,29.1,28.8,28.7,28.5,28.0,27.4,26.4,26.3,26.4,26.4,26.5,26.2,26.3,26.3,26.1};
            double y[] = {1.6,2.9,3.5,3.6,3.9, 4.0 ,4.4,4.8,5.0,5.1,4.8,4.1,2.7,2.4,3.2,3.5,3.9,3.8,3.4,2.7,2.2,2.6,3.0,2.5,2.9,3.2,3.3,3.5,3.4,3.2,3.3,2.9,1.9,1.5,2.2,2.5,2.7,3.1,3.1,3.1,3.4,3.0,2.4,1.6,1.5,2.7,3.0,3.0,2.9,3.2,3.3,3.2,2.8,2.3,1.7,1.5,1.4,0.8,1.2,2.0,2.8,3.5,3.6,3.3,3.7,3.6,3.0,2.3,2.3,3.0,3.0,2.8,2.9,2.8,2.6,2.3,2.3,2.8,3.3,4.3,4.7,4.4,4.0};
            
            for (int i = 0; i < 83 ; i++) {
                                 
                if(asig[i]==0){
                    c1.add(x[i],y[i]);
                }
                else if(asig[i]==1){
                    c2.add(x[i],y[i]);
                }
                else {
                    c3.add(x[i],y[i]);
                }
            }
            
            /*AGREGANDO CENTROIDES A LA GRAFICA*/
            for (int i = 0; i < centroides.numInstances(); i++) {
                Instance ins=centroides.instance(i);
                System.out.println("X: "+ins.toString(0) + " Y: "+ins.toString(1));
                
                if(asig[i]==0){
                    c1.add(Double.parseDouble(ins.toString(0)),Double.parseDouble(ins.toString(1)));
                }
                else if(asig[i]==1){
                    c2.add(Double.parseDouble(ins.toString(0)),Double.parseDouble(ins.toString(1)));
                }
                else {
                    c3.add(Double.parseDouble(ins.toString(0)),Double.parseDouble(ins.toString(1)));
                }
            }
            
            
            dataset.addSeries(c1);
            dataset.addSeries(c2);
            dataset.addSeries(c3);
                
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClusteringTaller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClusteringTaller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClusteringTaller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return dataset; 
    }
    
    public void modelo(){
        
         try {
            String ruta="temperatura-Viento.arff";
            Instances dataset=new Instances(new BufferedReader(new FileReader(ruta)));
            
            SimpleKMeans skm=new SimpleKMeans();
            skm.setNumClusters(3);
            skm.setPreserveInstancesOrder(true);
            skm.buildClusterer(dataset);
            
           System.out.println("Modelo: "+skm);
           
            Instances centroides=skm.getClusterCentroids();
            for (int i = 0; i < centroides.numInstances(); i++) {
                Instance ins=centroides.instance(i);
                System.out.println("X: "+ins.toString(0) + " Y: "+ins.toString(1));
            }
            
            int arr[]=skm.getAssignments();
            int arr1[]=skm.getClusterSizes();
            System.out.println("Asignaciones "+Arrays.toString(arr));
            System.out.println("Instancias por cluster "+Arrays.toString(arr1));
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClusteringTaller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClusteringTaller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClusteringTaller.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
    
}