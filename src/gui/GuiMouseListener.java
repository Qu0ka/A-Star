package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import main.AStar;
import main.Node;
import main.main;

/**
 *
 * @author hugop
 */
public class GuiMouseListener implements MouseListener {

    private Node node;

    public GuiMouseListener() {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        for (int i = 0; i < main.getGui().getListOfAll().size(); i++) {
            node = main.getGui().getListOfAll().get(i);
            if (main.getGui().getGenerateObstacles().isSelected()
                    && main.getGui().isAllowDrawing()
                    && e.getSource() == node.getNode()) {
                node.setObstacles(true);
                main.getGui().paintObstacles(node, main.getGui().getWidthSelectorSlider().getValue());
            }
            if (main.getGui().getEraseObstacles().isSelected()
                    && main.getGui().isAllowDrawing()
                    && e.getSource() == node.getNode()
                    && (node == main.getSwingWorker().getAStar().getStartNode()) == false
                    && (node == main.getSwingWorker().getAStar().getEndNode()) == false) {

                node.setObstacles(false);
                main.getGui().unpaintObstacles(node, main.getGui().getWidthSelectorSlider().getValue());
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == main.getGui().getStartAlgorithmButton() && main.getGui().getStartAlgorithmButton().isEnabled()) {
            if (main.getSwingWorker().getAStarList().isEmpty() == false && main.getGui().getChooseMode().isSelected()) {
                main.start();
            } else if (main.getGui().getChooseMode().isSelected() == false && main.getGui().getGenerateRandomGrid().isEnabled() == false) {
                main.start();
                System.out.println("coucou");
            }
        }

        if (e.getSource() == main.getGui().getGenerateRandomGrid() && main.getGui().getGenerateRandomGrid().isEnabled() && main.getGui().getChooseMode().isSelected() == false) {
            main.getSwingWorker().setAStar(new AStar());
            if (main.getSwingWorker().getAStar().getStartNode() == null && main.getSwingWorker().getAStar().getEndNode() == null) {
                main.getSwingWorker().getAStar().generateRandomGrid();
                main.getGui().getResetGrid().setEnabled(true);
                main.getGui().getGenerateRandomGrid().setEnabled(false);
            }
        }

        if (e.getSource() == main.getGui().getResetGrid() && main.getGui().getResetGrid().isEnabled()) {
            // Reset differents parameters to keep the validty of our code
            for (int i = 0; i < main.getSwingWorker().getAStarList().size(); i++) {
                main.getSwingWorker().getAStarList().get(i).getClosedList().clear();
                main.getSwingWorker().getAStarList().get(i).getListOfNodes().clear();
                main.getSwingWorker().getAStarList().get(i).getNeighbours().clear();
                main.getSwingWorker().getAStarList().get(i).getOpenList().clear();
                main.getSwingWorker().getAStarList().get(i).setEndNode(null);
                main.getSwingWorker().getAStarList().get(i).setStartNode(null);
            }
            main.getSwingWorker().getAStarList().clear();
            main.getSwingWorker().getRunAlgoList().clear();
            main.setEndNodeCount(0);
            main.getGui().getContentPanel().removeAll();
            main.getGui().getListOfAll().clear();
            main.getGui().getGenerateObstacles().setEnabled(true);
            main.getGui().getEraseObstacles().setEnabled(true);
            main.getGui().getChooseStartPos().setEnabled(true);
            main.getGui().getChooseEndPos().setEnabled(true);
            main.getGui().getResetGrid().setEnabled(false);
            main.getGui().getGenerateRandomGrid().setEnabled(true);
            main.getGui().getStartAlgorithmButton().setEnabled(true);
            main.getGui().getWindowMenu().setEnabled(true);
            main.getGui().getChooseMode().setEnabled(true);
            main.getGui().getChooseMode().setSelected(false);
            main.getGui().getGui().repaint();
            main.getGui().createNodes(main.getGui().getMatrixSize());
            main.getGui().getGui().revalidate();
            main.getSwingWorker().setRunAlgo(true);
        }
        // Iterate trough all the buttons
        for (int i = 0; i < main.getGui().getListOfAll().size(); i++) {
            if (main.getGui().getChooseStartPos().isSelected() && e.getSource() == main.getGui().getListOfAll().get(i).getNode()) {
                // Generate the startNode and add it to the openList and paint it on the GUI
                for (int j = 0; j < main.getSwingWorker().getAStarList().size(); j++) {
                    main.getSwingWorker().getAStarList().get(j).setStartNode(main.getGui().getListOfAll().get(i));
                    main.getSwingWorker().getAStarList().get(j).getOpenList().add(main.getSwingWorker().getAStarList().get(j).getStartNode());
                    main.getSwingWorker().getAStarList().get(j).getStartNode().setOpenList(true);
                    main.getGui().paintStartNode(main.getSwingWorker().getAStarList().get(j).getStartNode());
                }
                if (main.getSwingWorker().getAStarList().isEmpty()) {
                    JOptionPane.showMessageDialog(main.getGui().getGui(), "First add the end nodes", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            if (main.getGui().getChooseEndPos().isSelected() && e.getSource() == main.getGui().getListOfAll().get(i).getNode()) {
                main.getSwingWorker().setAStar(new AStar(main.getGui().getListOfAll().get(i)));
                main.getSwingWorker().getAStarList().add(main.getSwingWorker().getAStar());
                main.getSwingWorker().getRunAlgoList().add(true);
                main.getGui().paintEndNode(main.getGui().getListOfAll().get(i));
                int endNodeCount = main.getEndNodeCount() + 1;
                main.setEndNodeCount(endNodeCount);
                System.out.println(main.getEndNodeCount());

            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
