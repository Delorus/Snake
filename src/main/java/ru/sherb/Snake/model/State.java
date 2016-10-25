package ru.sherb.Snake.model;

import java.awt.*;


public enum State {
    EMPTY {
//        @Override
//        public Color getColor(Cell cell) {
//            return cell.getColor(); //new Color(255, 255, 255); //white
//        }
    },
    SNAKE {
//        @Override
//        public Color getColor(Cell snake) {
//            return snake.getColor(); //new Color(0, 0, 0); //black
//        }
    },
    FRUIT {
//        @Override
//        public Color getColor(Cell fruit) {
//            return fruit.getColor(); //new Color(0, 255, 0); //green
//        }
    };
    //TODO сделать возможность задания пользовательского цвета
    //TODO избавиться от этого несоответствие всем парадигмам здравого смысла
//    public abstract Color getColor(Cell cell);
}
