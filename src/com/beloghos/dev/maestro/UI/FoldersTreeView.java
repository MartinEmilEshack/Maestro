package com.beloghos.dev.maestro.UI;

import java.io.File;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FoldersTreeView extends TreeView<String> {

    public FoldersTreeView(String path){
        super();
        getFoldersTreeView(path);
    }

    private void getFoldersTreeView(String rootFolder) {

        File rootFile = new File(rootFolder);

        ImageView rootIcon = new ImageView(new Image(getClass().getResourceAsStream("LibraryIcon.png")));
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
                    if (!file.isFile())
                        folderItem.getChildren().add(new TreeItem<>("DUMMY"));
                }
        );

        super.setShowRoot(true);
        super.setRoot(root);

//        TreeView<String> folderTreeView = new TreeView<>();
//        folderTreeView.setShowRoot(true);
//        folderTreeView.setRoot(root);

//        return folderTreeView;

    }

}
