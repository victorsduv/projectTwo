package academy.teenfuture.projectTwo;

import org.junit.jupiter.api.ClassDescriptor;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.ClassOrdererContext;

import java.util.Comparator;

public class MyOrderer implements ClassOrderer {
    // This class remains empty, it is used only as a holder for the above annotations
    @Override
    public void orderClasses(ClassOrdererContext context) {
        context.getClassDescriptors().sort(Comparator.comparingInt(this::getOrder));
    }

    private int getOrder(ClassDescriptor descriptor) {
        String className = descriptor.getTestClass().getSimpleName();
        switch (className) {
            case "OpenCartTest":
                return 6; 
            case "CommonCartTests":
                return 2;
            case "CommonNonEmptyCartTests":
                return 3;
            case "GuestCheckCartTests":
                return 4;
            case "MemberCheckCartTest":
                return 5;
            case "checkoutTests":
                return 1;
            default:
                return Integer.MAX_VALUE; // Fallback for unordered classes
        }
    }
}