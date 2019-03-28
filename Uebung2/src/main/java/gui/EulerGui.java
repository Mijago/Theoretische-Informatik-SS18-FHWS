package gui;

import com.mxgraph.layout.*;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;

import javax.swing.*;
import java.awt.*;

public class EulerGui {

    private JPanel main;

    public static JFrame showGraph(Graph<String, DefaultWeightedEdge> graph) {
        JFrame frame = new JFrame("EulerGui");
        frame.setContentPane(new EulerGui().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Graph");
        frame.setMinimumSize(new Dimension(230, 100));


        JGraphXAdapter<String, DefaultWeightedEdge> jgxAdapterContext = new JGraphXAdapter<String, DefaultWeightedEdge>(graph);
        // jgxAdapterContext.getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_NOLABEL, "1");
        // jgxAdapterContext.getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_ENDARROW, "0");
        jgxAdapterContext.setCellsEditable(false);
        //jgxAdapterContext.setCellsMovable(false);

        jgxAdapterContext.setEdgeLabelsMovable(false);
        jgxAdapterContext.setCellsDeletable(false);
        jgxAdapterContext.setCellsDisconnectable(false);
        jgxAdapterContext.setCellsResizable(false);
        jgxAdapterContext.setCellsBendable(false);


        mxGraphComponent mxGraphComponent = new mxGraphComponent(jgxAdapterContext);

        Container contentPane = frame.getContentPane();
        contentPane.add(mxGraphComponent, BorderLayout.CENTER);


        //mxGraphLayout layout = new mxCircleLayout(jgxAdapterContext, graph.vertexSet().size()*20);
        //((mxCircleLayout) layout).setMoveCircle(false);

        mxHierarchicalLayout layout = new mxHierarchicalLayout(jgxAdapterContext);

        layout.execute(jgxAdapterContext.getDefaultParent());

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return frame;
    }
}
