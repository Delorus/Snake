package ru.sherb.Snake.model;

//TODO [REFACTOR] переместить статусы каждый в свой объект либо добавить их в GameObject
//TODO сделать другие игровые объекты, в том числе shit, wall
public enum State {
    EMPTY {
//        @Override
//        public Color getColor(Cell cell) {
//            return cell.getColor(); //new Color(255, 255, 255); //white
//        }
    },
    SNAKE {
//        @Override
//        public Color getColor(Cell Snake) {
//            return Snake.getColor(); //new Color(0, 0, 0); //black
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
