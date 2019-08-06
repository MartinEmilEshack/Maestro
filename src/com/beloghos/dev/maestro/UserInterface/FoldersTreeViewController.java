package com.beloghos.dev.maestro.userInterface;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.beloghos.dev.maestro.ImageSources.ImageURIs;
import com.beloghos.dev.maestro.Main;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class FoldersTreeViewController extends TreeView<String> implements Initializable {

    public FoldersTreeViewController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FoldersTreeView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getFoldersTreeView(Main.MEMORY.getURI().getPath());
    }

    private void getFoldersTreeView(String rootFolder) {

        File rootFile = new File(rootFolder);

        ImageView rootIcon = new ImageView(new Image(ImageURIs.LibraryMusicFolderIcon));
        rootIcon.setFitHeight(20);
        rootIcon.setFitWidth(20);

        FolderTreeItem root = new FolderTreeItem(rootFile.getName(),rootFile,rootIcon);
        root.getChildren().add(new TreeItem<>("DUMMY"));

        root.addEventHandler(FolderTreeItem.branchExpandedEvent(),
                (FolderTreeItem.TreeModificationEvent<String> treeModificationEvent) -> {
                    FolderTreeItem folderItem = (FolderTreeItem) treeModificationEvent.getTreeItem();
                    folderItem.getChildren().clear();
                    File expandedRoot = folderItem.getFile();
                    File[] files = expandedRoot.listFiles();
                    if (files != null)
                        for (File file : files) {
                            FolderTreeItem folder = new FolderTreeItem(file.getName(), file);
                            if(!file.isFile()){
                                File[] filteredFiles = file.listFiles(pathname -> !pathname.isFile());
                                if(filteredFiles != null && filteredFiles.length != 0)
                                    folder.getChildren().add(new TreeItem<>("DUMMY"));
                                folderItem.getChildren().add(folder);
                            }
                        }
                }
        );

        root.addEventHandler(FolderTreeItem.branchCollapsedEvent(),
                (FolderTreeItem.TreeModificationEvent<String> treeModificationEvent) -> {
                    FolderTreeItem folderItem = (FolderTreeItem) treeModificationEvent.getTreeItem();
                    folderItem.getChildren().clear();
                    File file = folderItem.getFile();
                    File[] filteredFiles = file.listFiles(pathname -> !pathname.isFile());
                    if (!file.isFile() && filteredFiles != null && filteredFiles.length != 0)
                            folderItem.getChildren().add(new TreeItem<>("DUMMY"));
                }
        );

        this.setOnMouseClicked((mouseEvent -> {
            FolderTreeItem item = (FolderTreeItem) this.getSelectionModel().getSelectedItem();
//            System.out.println(item);
//            folderPicked(item);
            if(item != null)
                Main.MEMORY.getURI().setPath(item.getFile().getPath());
        }));

        this.setOnKeyReleased(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                FolderTreeItem item = (FolderTreeItem) this.getSelectionModel().getSelectedItem();
//                System.out.println(item);
//                folderPicked(item);
                Main.MEMORY.getURI().getPathProperty().setValue(item.getFile().getPath());
            }
        });

        super.setShowRoot(true);
        super.setRoot(root);

//        TreeView<String> folderTreeView = new TreeView<>();
//        folderTreeView.setShowRoot(true);
//        folderTreeView.setRoot(root);

//        return folderTreeView;

    }

//    private void folderPicked(FolderTreeItem item){
//        if(item != null)
//            this.fireEvent(new FolderSelectedEvent(item,this,FolderSelectedEvent.FOLDER_SELECTED,item.getFile().getPath()));
//    }

}
