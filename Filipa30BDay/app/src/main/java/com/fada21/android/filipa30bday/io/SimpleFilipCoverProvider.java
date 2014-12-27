package com.fada21.android.filipa30bday.io;

import com.fada21.android.filipa30bday.model.FilipCover;

import java.util.ArrayList;
import java.util.List;

public class SimpleFilipCoverProvider implements IFilipCoverProvider {

    private SimpleFilipCoverProvider() {}

    public static IFilipCoverProvider createProvider() {
        return new SimpleFilipCoverProvider();
    }

    @Override
    public List<FilipCover> getFilipPicsList() {
        ArrayList<FilipCover> filipPicsList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            filipPicsList.add(getSimpleFilipCover(i));
        }
        return filipPicsList;
    }

    private FilipCover getSimpleFilipCover(int id) {
        new FilipCover();
        FilipCover filipCover = new FilipCover(id, "Filip" + id, "Autor " + id, "Filip Nowakowski ma słabostki : " + id, "https://lh3.googleusercontent.com/ZUcR1CoYC3ZTy6cd6gBBgAoeCbRlzXIf-p5DWdnZIz9Df4WzAEZxfSLy_NE-f2usswnmNw=w1896-h850");
        return filipCover;
    }
}
