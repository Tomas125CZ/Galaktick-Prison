package tomas.enchantments;

import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CustomEnchants {

    public static final Enchantment EXPLOSIVE = new EnchantmentWrapper("explosive", "Explosive", 100);
    public static final Enchantment SPEED = new EnchantmentWrapper("speed", "Speed", 3);
    public static final Enchantment JUMP = new EnchantmentWrapper("jump", "Jump", 3);
    public static final Enchantment FLY = new EnchantmentWrapper("fly", "Fly", 5);
    public static final Enchantment JACKHAMMER = new EnchantmentWrapper("jackhammer", "JackHammer", 20);

    public static void register() {
        boolean registeredExplosive = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(CustomEnchants.EXPLOSIVE);
        boolean registeredSpeed = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(CustomEnchants.SPEED);
        boolean registeredJump = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(CustomEnchants.JUMP);
        boolean registeredFly = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(CustomEnchants.FLY);
        boolean registeredJackHammer = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(CustomEnchants.JACKHAMMER);

        if(!registeredExplosive)
            registerEnchantment(EXPLOSIVE);
        if(!registeredSpeed)
            registerEnchantment(SPEED);
        if(!registeredJump)
            registerEnchantment(JUMP);
        if(!registeredFly)
            registerEnchantment(FLY);
        if(!registeredJackHammer)
            registerEnchantment(JACKHAMMER);
    }

    public static void registerEnchantment(Enchantment enchantment) {
        boolean registered = true;
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch(Exception e) {
            registered = false;
            e.printStackTrace();
        }
        if(registered) {
            System.out.println("ENCHANTMENT " + enchantment.getName() + " BYL ÚSPĚŠNĚ VYTVOŘEN");
        }
    }
}
