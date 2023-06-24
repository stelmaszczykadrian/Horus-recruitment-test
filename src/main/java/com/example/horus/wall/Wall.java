package com.example.horus.wall;

import java.util.*;
import java.util.stream.Stream;

public class Wall implements Structure {
    private List<Block> blocks;

    public Wall(List<Block> blocks) {
        if (blocks == null) {
            throw new IllegalArgumentException("Blocks cannot be null.");
        }
        this.blocks = blocks;
    }

    // zwraca dowolny element o podanym kolorze
    @Override
    public Optional<Block> findBlockByColor(String color) {
        return blocks.stream()
                .flatMap(block -> {
                    if (block instanceof CompositeBlock) {
                        return ((CompositeBlock) block).getBlocks().stream();
                    } else {
                        return Stream.of(block);
                    }
                })
                .filter(block -> block.getColor().equals(color))
                .findAny();
    }

    // zwraca wszystkie elementy z danego materiału
    @Override
    public List<Block> findBlocksByMaterial(String material) {
        List<Block> blocksWithMaterial = new ArrayList<>();

        for (Block block : blocks) {
            findBlocksByMaterialRecursive(block, material, blocksWithMaterial);
        }

        return blocksWithMaterial;
    }

    private void findBlocksByMaterialRecursive(Block block, String material, List<Block> blocksWithMaterial) {
        if (block instanceof CompositeBlock compositeBlock) {
            for (Block nestedBlock : compositeBlock.getBlocks()) {
                findBlocksByMaterialRecursive(nestedBlock, material, blocksWithMaterial);
            }
        } else {
            if (block.getMaterial().equals(material)) {
                blocksWithMaterial.add(block);
            }
        }
    }

    //zwraca liczbę wszystkich elementów tworzących strukturę
    @Override
    public int count() {
        int count = 0;

        for (Block block : blocks) {
            count += countBlocks(block);
        }

        return count;
    }

    private int countBlocks(Block block) {
        int SINGLE_BLOCK_COUNT = 1;

        if (block instanceof CompositeBlock compositeBlock) {
            for (Block nestedBlock : compositeBlock.getBlocks()) {
                SINGLE_BLOCK_COUNT += countBlocks(nestedBlock);
            }
        }
        return SINGLE_BLOCK_COUNT;
    }
}

