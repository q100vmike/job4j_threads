package ru.job4j.fork;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.User;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ParallelIndexSearchTest {

    List<User> list = new ArrayList<>();
    List<User> shortList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        for (int i = 0; i < 100; i++) {
            list.add(User.of("User" + i));
        }

        for (int i = 0; i < 5; i++) {
            shortList.add(User.of("User" + i));
        }
    }

    @Test
    void testSearchIndex33() {
        User user = User.of("User33");
        Integer result = ParallelIndexSearch.goSearch(list, user);
        assertThat(result).isEqualTo(33);
    }

    @Test
    void testSearchIndex99() {
        User user = User.of("User99");
        Integer result = ParallelIndexSearch.goSearch(list, user);
        assertThat(result).isEqualTo(99);
    }

    @Test
    void testSearchIndexNof() {
        User user = User.of("User00");
        Integer result = ParallelIndexSearch.goSearch(list, user);
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void testSearchIndexShortList0() {
        User user = User.of("User0");
        Integer result = ParallelIndexSearch.goSearch(list, user);
        assertThat(result).isEqualTo(0);
    }

    @Test
    void testSearchIndexDiffTypesStr() {
        Integer result = ParallelIndexSearch.goSearch(list, "user");
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void testSearchIndexDiffTypesInt() {
        User user = User.of("User0");
        Integer result = ParallelIndexSearch.goSearch(list, 0);
        assertThat(result).isEqualTo(-1);
    }
}