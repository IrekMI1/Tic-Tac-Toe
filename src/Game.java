import java.util.Random;
import java.util.Scanner;

/**
 * Класс, описывающий игру "крестики-нолики"
 */
public class Game {
    private static int fieldSize;
    private static final int WIN_COUNT = 3;
    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = 'O';
    private static final char DOT_EMPTY = '.';
    private static final Scanner SCANNER = new Scanner(System.in);
    private static char[][] field;
    private static final Random RANDOM = new Random();

    /**
     * Метод, который инициализирует игру, устанавливая размер стороны квадратного поля и заполняя ее символами ' . '
     * @param size размер стороны квадратного поля
     */
    private static void initialize(int size) {
        fieldSize = size;
        field = new char[fieldSize][fieldSize];
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                field[i][j] = DOT_EMPTY;
            }
        }
    }

    /**
     * Метод, выводящий информацию о состоянии игрового поля на консоль
     */
    private static void printField() {
        for (int i = 0; i < fieldSize; i++) {
            System.out.print("| ");
            for (int j = 0; j < fieldSize; j++) {
                System.out.print(field[i][j] + " | ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Метод, который позволяет сделать ход пользователю. Пользователь через консоль вводит
     * координаты поля через пробел (строка столбец), на который ставится "крестик". При вводе невалиных координат
     * повторно запрашиваются кначения
     */
    private static void humanTurn() {
        int x, y;

        do {
            System.out.printf("Введите кооринаты X и Y (от 1 до %d) через пробел:\n", fieldSize);
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        } while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[x][y] = DOT_HUMAN;
    }

    /**
     * Метод хода компьютера, который случайным образом выбирает координаты поля, чтобы поставить "нолик".
     * При генерации координаты проверяются на валидность: ячейка пустая и координата не выходит за рамки поля
     */
     private static void aiTurn() {
        int x, y;
        do {
            x = RANDOM.nextInt(fieldSize);
            y = RANDOM.nextInt(fieldSize);
        } while (!isCellEmpty(x, y));
        field[x][y] = DOT_AI;
    }

    /**
     * Метод проверяет статус игры: наличие победителя или ничья
     * @param symbol символ игрока
     * @param message передаваемое сообщение, которое выводится на консоль
     * @return true, если есть победитель или ничья; false, если нисего не определено
     */
    private static boolean gameCheck(char symbol, String message) {
        if (checkWin(symbol)) {
            System.out.println(message);
            return true;
        }
        if (checkDraw()) {
            System.out.println("Ничья!");
            return true;
        }
        return false;
    }

    /**
     * Проверка игры на ничью
     * @return true, если все ячейки не пустые; false - есть пустая ячейка
     */
    private static boolean checkDraw() {
        for (int x = 0; x < fieldSize; x++) {
            for (int y = 0; y < fieldSize; y++) {
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }

    /**
     * Метод проверки поля на наличие победителя. Происходит последовательная проверка по горизонтали,
     * вертикали и двум диагоналям
     * @param symbol символ игрока
     * @return true, если символы расположены по всей линии поля по одной из направлений;
     * false - нет совпадений
     */
    private static boolean checkWin(char symbol) {
//      Проверка по горизонтали:
        for (int x = 0; x < fieldSize; x++) {
            for (int y = 0; y < fieldSize; y++) {
                if (field[x][y] != symbol) {
                    break;
                } else if (y == (fieldSize - 1)) {
                    return true;
                }
            }
        }

//      Проверка по вертикали
        for (int y = 0; y < fieldSize; y++) {
            for (int x = 0; x < fieldSize; x++) {
                if (field[x][y] != symbol) {
                    break;
                } else if (x == (fieldSize - 1)) {
                    return true;
                }
            }
        }

//        Проверка по двум диагоналям
        for (int x = 0; x < fieldSize; x++) {
            if (field[x][x] != symbol) {
                break;
            } else if (x == fieldSize - 1) {
                return true;
            }
        }

        for (int x = 0; x < fieldSize; x++) {
            if (field[fieldSize - 1 - x][x] != symbol) {
                break;
            } else if (x == fieldSize - 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Метод проверяет координаты на нахождение в пределах игрового поля
     * @param x координаа строки
     * @param y координата столбца
     * @return true, если координаты в пределах игрового поля;
     * false - если за пределами поля
     */
    private static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSize && y >= 0 && y < fieldSize;
    }

    /**
     * Метод проверяет ячейку на пустоту.
     * @param x координата строки
     * @param y координата столбца
     * @return true, если ячека с введенной координатой пуста;
     * false - если ячейка занята символом игрока
     */
    private static boolean isCellEmpty(int x, int y) {
        return field[x][y] == DOT_EMPTY;
    }

    /**
     * Метод, который является точкой входа в игру.
     * @param args стандартные аргументы метода main
     */
    public static void main(String[] args) {
        Game.initialize(3);

        while (true) {
            Game.humanTurn();
            Game.printField();
            if (Game.gameCheck(DOT_HUMAN, "You won!")) break;

            Game.aiTurn();
            Game.printField();
            if (Game.gameCheck(DOT_AI, "Computer won!")) break;
        }
    }
}
