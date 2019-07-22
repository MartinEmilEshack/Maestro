package com.beloghos.dev.maestro.UserInterface;

import java.io.File;

import com.beloghos.dev.maestro.ImageSources.ImageURIs;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class FoldersTreeView extends TreeView<String> {

    public FoldersTreeView(String path){
        super();
        getFoldersTreeView(path);
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
            folderPicked(item);
        }));

        this.setOnKeyReleased(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                FolderTreeItem item = (FolderTreeItem) this.getSelectionModel().getSelectedItem();
//                System.out.println(item);
                folderPicked(item);
            }
        });

        super.setShowRoot(true);
        super.setRoot(root);

//        TreeView<String> folderTreeView = new TreeView<>();
//        folderTreeView.setShowRoot(true);
//        folderTreeView.setRoot(root);

//        return folderTreeView;

    }

    private void folderPicked(FolderTreeItem item){
        if(item != null)
            this.fireEvent(new FolderSelectedEvent(item,this,FolderSelectedEvent.FOLDER_SELECTED,item.getFile().getPath()));
    }

}
