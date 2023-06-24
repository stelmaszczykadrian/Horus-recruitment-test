package com.example.horus.wall;

import java.util.List;

public class CompositeBlockImpl implements CompositeBlock {
    private List<Block> blocks;

    public CompositeBlockImpl(List<Block> nestedBlocks) {
        this.blocks = nestedBlocks;
    }

    @Override
    public List<Block> getBlocks() {
        return blocks;
    }

    @Override
    public String getColor() {
        throw new UnsupportedOperationException("Composite block has no color.");
    }

    @Override
    public String getMaterial() {
        throw new UnsupportedOperationException("Composite block has no material.");
    }
}
