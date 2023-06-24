package com.example.horus;

import com.example.horus.wall.Block;
import com.example.horus.wall.CompositeBlockImpl;
import com.example.horus.wall.SingleBlock;
import com.example.horus.wall.Wall;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WallTest {

    @Test
    void findBlockByColor_ReturnsMatchingBlock_WhenWallHasPinkBlockAndSearchedColorIsPink() {
        //Given
        String color = "Pink";
        Block block = new SingleBlock(color, "Stone");
        Wall wall = new Wall(List.of(block));

        //When
        Optional<Block> actualBlock = wall.findBlockByColor(color);

        //Then
        assertTrue(actualBlock.isPresent());
        assertEquals(block, actualBlock.get());
    }

    @Test
    void findBlockByColor_ReturnsEmptyOptional_WhenWallHasPinkBlockAndSearchedColorIsBlack() {
        //Given
        String color = "Black";
        Block block = new SingleBlock("Pink", "Stone");
        Wall wall = new Wall(List.of(block));

        //When
        Optional<Block> result = wall.findBlockByColor(color);

        //Then
        assertFalse(result.isPresent());
    }

    @Test
    void findBlockByColor_ReturnsBlockWithCorrectColor_WhenBlockHasCompositeBlock() {
        // Given
        String color = "Blue";

        List<Block> blocks = new ArrayList<>();

        List<Block> nestedBlocks = new ArrayList<>();

        nestedBlocks.add(new SingleBlock("Blue", "Wood"));
        nestedBlocks.add(new SingleBlock("Red", "Metal"));
        nestedBlocks.add(new SingleBlock("Green", "Stone"));

        Block nestedCompositeBlock = new CompositeBlockImpl(nestedBlocks);

        blocks.add(nestedCompositeBlock);

        Wall wall = new Wall(blocks);

        // When
        Optional<Block> block = wall.findBlockByColor(color);

        // Then
        assertTrue(block.isPresent());
        assertEquals(color, block.get().getColor());
    }

    @Test
    void findBlockByColor_ReturnsEmptyOptional_WhenWallHasEmptyList() {
        // Given
        String color = "Red";
        Wall wall = new Wall(Collections.emptyList());

        // When
        Optional<Block> result = wall.findBlockByColor(color);

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void findBlockByColor_ReturnsEmptyOptional_WhenWallHasNullColor() {
        // Given
        String color = null;
        Block block = new SingleBlock("Yellow", "Metal");
        Wall wall = new Wall(Collections.singletonList(block));

        // When
        Optional<Block> result = wall.findBlockByColor(color);

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void findBlockByColor_ReturnsEmptyOptional_WhenWallHasEmptyStringAsColor() {
        // Given
        String color = "";
        Block block = new SingleBlock("Green", "Stone");
        Wall wall = new Wall(Collections.singletonList(block));

        // When
        Optional<Block> result = wall.findBlockByColor(color);

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void findBlocksByMaterial_ReturnsEmptyList_WhenWallHasNullMaterial() {
        // Given
        String material = null;
        Block block = new SingleBlock("Blue", "Metal");
        Wall wall = new Wall(Collections.singletonList(block));

        // When
        List<Block> result = wall.findBlocksByMaterial(material);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void findBlocksByMaterial_ReturnsEmptyList_WhenWallHasEmptyStringAsMaterial() {
        // Given
        String material = "";
        Block block = new SingleBlock("Red", "Wood");
        Wall wall = new Wall(Collections.singletonList(block));

        // When
        List<Block> result = wall.findBlocksByMaterial(material);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void findBlocksByMaterial_ReturnsMatchingBlocks_WhenWallHasRecursiveBlocks() {
        //Given
        List<Block> blocks = new ArrayList<>();

        Block block1 = new SingleBlock("Yellow", "Metal");
        Block block2 = new SingleBlock("Pink", "Stone");
        Block block3 = new SingleBlock("Orange", "Stone");
        Block block4 = new SingleBlock("Red", "Metal");

        blocks.add(block1);
        blocks.add(block2);
        blocks.add(block3);
        blocks.add(block4);

        List<Block> nestedBlocks = new ArrayList<>();

        nestedBlocks.add(new SingleBlock("Blue", "Wood"));
        nestedBlocks.add(new SingleBlock("Blue", "Metal"));
        nestedBlocks.add(new SingleBlock("Green", "Stone"));
        nestedBlocks.add(new SingleBlock("Green", "Stone"));

        Block nestedCompositeBlock = new CompositeBlockImpl(nestedBlocks);
        List<Block> superNestedBlocks = new ArrayList<>();
        superNestedBlocks.add(new SingleBlock("Purple", "Wood"));
        superNestedBlocks.add(new SingleBlock("Purple", "Metal"));
        superNestedBlocks.add(new SingleBlock("Yellow", "Stone"));
        superNestedBlocks.add(nestedCompositeBlock);

        Block superNestedCompositeBlock = new CompositeBlockImpl(superNestedBlocks);
        blocks.add(superNestedCompositeBlock);

        Wall wall = new Wall(blocks);

        //When
        List<Block> blocksWithMaterial = wall.findBlocksByMaterial("Stone");

        //Then
        int expectedCount = 5;
        assertEquals(expectedCount, blocksWithMaterial.size());
    }

    @Test
    void constructor_ThrowsIllegalArgumentException_WhenWallInitializedWithNull() {
        assertThrows(IllegalArgumentException.class, () -> new Wall(null));
    }


    @Test
    void count_ReturnsTotalBlockCount_WhenWallHasNestedBlocks() {
        //Given
        List<Block> blocks = new ArrayList<>();

        Block block1 = new SingleBlock("Yellow", "Metal");
        Block block2 = new SingleBlock("Pink", "Stone");
        Block block3 = new SingleBlock("Orange", "Stone");
        Block block4 = new SingleBlock("Red", "Metal");

        blocks.add(block1);
        blocks.add(block2);
        blocks.add(block3);
        blocks.add(block4);

        List<Block> nestedBlocks = new ArrayList<>();

        nestedBlocks.add(new SingleBlock("Blue", "Wood"));
        nestedBlocks.add(new SingleBlock("Blue", "Metal"));
        nestedBlocks.add(new SingleBlock("Green", "Stone"));
        nestedBlocks.add(new SingleBlock("Green", "Stone"));

        Block nestedCompositeBlock = new CompositeBlockImpl(nestedBlocks);
        List<Block> superNestedBlocks = new ArrayList<>();
        superNestedBlocks.add(new SingleBlock("Purple", "Wood"));
        superNestedBlocks.add(new SingleBlock("Purple", "Metal"));
        superNestedBlocks.add(new SingleBlock("Yellow", "Stone"));
        superNestedBlocks.add(nestedCompositeBlock);

        Block superNestedCompositeBlock = new CompositeBlockImpl(superNestedBlocks);
        blocks.add(superNestedCompositeBlock);

        Wall wall = new Wall(blocks);

        //When
        int actualCount = wall.count();

        //Then
        int singleBlockCount = 11;
        int compositeBlockCount = 2;

        int expectedCount = singleBlockCount + compositeBlockCount;

        assertEquals(expectedCount, actualCount);
    }

    @Test
    void count_ReturnsTotalBlockCount_WhenWallHasNoNestedBlocks() {
        //Given
        List<Block> blocks = new ArrayList<>();

        Block block1 = new SingleBlock("Yellow", "Metal");
        Block block2 = new SingleBlock("Pink", "Stone");
        Block block3 = new SingleBlock("Blue", "Wood");

        blocks.add(block1);
        blocks.add(block2);
        blocks.add(block3);

        Wall wall = new Wall(blocks);

        //When
        int actualCount = wall.count();

        //Then
        int expectedCount = 3;

        assertEquals(expectedCount, actualCount);
    }

}
