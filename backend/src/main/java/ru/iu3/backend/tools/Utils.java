package ru.iu3.backend.tools;
import org.springframework.security.crypto.codec.Hex;
import java.security.MessageDigest;

/*
    Функция ComputeHash, которая вычисляет SHA-256 по конкатенации строк пароля и salt потребуется в
    UserController для смены пароля пользователя, и в LoginController для проверки пароля. Эта функция
    получает на вход пароль в виде обычной строки и salt в виде необычной строки, в формате HEX-ASCII. salt
    это случайная последовательность байтов, которая генерируется каждый раз при смене пароля. Перед тем
    как передать salt функции ComputeHash массив байтов преобразуется в строку, каждая пара символов
    которой представляет в шестнадцатеричной системе в текстовом виде один байт. В такой строке могут быть
    только цифры от 0 до 9 и буквы от A до F. В этой функции пароль преобразуется в массив байтов, затем этот
    массив преобразуется в HEXASCII и склеивается с salt. Затем все это преобразуется обратно в байты и по
    этим байтам считается хеш SHA256. Это значение преобразуется в HEX-ASCII и возвращается как результат
    работы функции.
 */
public class Utils {
    public static String ComputeHash(String pwd, String salt)
    {
        MessageDigest digest;
        byte[] w = Hex.decode(new String(Hex.encode(pwd.getBytes())) + salt);
        try {
            digest = MessageDigest.getInstance("SHA-256");
        }
        catch (Exception ex) {
            return new String();
        }
        return new String(Hex.encode(digest.digest(w)));
    }
}

/*
    Хранить пароли в базе в чистом виде не рекомендуется по соображениям безопасности, но
    там можно хранить хэши паролей. Функция вычисления хэша работает в одну сторону, поэтому из хэша
    нельзя восстановить пароль, но можно проверить введенный пароль, вычислив его хэш и сравнив с копией в
    базе. Зачем нужен salt? Вы можете использовать пароли qwerty или 123 или что то в этом роде. Можно
    составить словарь состоящий их хэшей таких простых паролей и легко подобрать нужное значение. Кроме
    этого, если у двух пользователей в системе будут одинаковые пароли, то и хэши у них будут одинаковые. Для
    того чтобы избавится от этих слабых мест в защите, достаточно добавить к паролю случайную
    последовательность байтов. Точнее делать это каждый раз, когда пароль изменяется.
 */