package com.example.horus.wall;

import java.util.List;

public interface CompositeBlock extends Block {
    List<Block> getBlocks();
}
