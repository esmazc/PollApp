package ba.etf.rma22.projekat

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.CoreMatchers.`is` as Is


@RunWith(AndroidJUnit4::class)
class MySpirala2AndroidTest {
    @get:Rule
    val intentsTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun zadatak1Test() {
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToFirst())
        onView(withId(R.id.filterAnketa)).check(matches(isDisplayed()))
        //onView(Is(instanceOf(FragmentAnkete::class.java))).check(matches(isDisplayed()))
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToLast())
        onView(withId(R.id.dodajIstrazivanjeDugme)).check(matches(isDisplayed()))
        //onView(Is(instanceOf(FragmentIstrazivanje::class.java))).check(matches(isDisplayed()))
        onView(withId(R.id.odabirGodina)).perform(click())
        onData(allOf(Is(instanceOf(String::class.java)), Is("4"))).perform(click())
        onView(withId(R.id.odabirIstrazivanja)).perform(click())
        onData(allOf(Is(instanceOf(String::class.java)), Is("Istraživanje broj 5"))).perform(click())
        onView(withId(R.id.odabirGrupa)).perform(click())
        onData(allOf(Is(instanceOf(String::class.java)), Is("Grupa3"))).perform(click())
        onView(withId(R.id.dodajIstrazivanjeDugme)).perform(click())
        onView(withId(R.id.tvPoruka)).check(matches(isDisplayed()))
        //onView(Is(instanceOf(FragmentPoruka::class.java))).check(matches(isDisplayed()))
        onView(withSubstring("Uspješno ste upisani")).check(matches(isDisplayed()))
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToFirst())
        onView(withId(R.id.filterAnketa)).check(matches(isDisplayed()))
        //onView(Is(instanceOf(FragmentAnkete::class.java))).check(matches(isDisplayed()))
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToLast())
        onView(withId(R.id.dodajIstrazivanjeDugme)).check(matches(isDisplayed()))
    }

    @Test
    fun zadatak2Test() {
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToFirst())
        onView(withId(R.id.filterAnketa)).perform(click())
        onData(allOf(CoreMatchers.`is`(instanceOf(String::class.java)), CoreMatchers.`is`("Sve moje ankete"))).perform(click())
        val ankete = AnketaRepository.getMyAnkete()
        onView(withId(R.id.listaAnketa)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(allOf(hasDescendant(withText(ankete[3].naziv)),
            hasDescendant(withText(ankete[3].nazivIstrazivanja))), click()))
        val odgovori = PitanjeAnketaRepository.getPitanja(ankete[3].naziv, ankete[3].nazivIstrazivanja)[0].opcije
        onData(anything()).inAdapterView(withId(R.id.odgovoriLista)).atPosition(0).perform(click())
        onView(allOf(withText(odgovori[0]))).check(matches(withTextColor(R.color.answer_click)))
        onView(withId(R.id.dugmeZaustavi)).perform(click())
        onView(withId(R.id.listaAnketa)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(allOf(hasDescendant(withText(ankete[3].naziv)),
            hasDescendant(withText(ankete[3].nazivIstrazivanja))), click()))
        onView(allOf(withText(odgovori[0]))).check(matches(withTextColor(R.color.answer_click)))
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToLast())
        onView(withSubstring("%")).check(matches(isDisplayed()))
        onView(withId(R.id.dugmePredaj)).perform(click())
        onView(withSubstring("Završili ste anketu")).check(matches(isDisplayed()))
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(1))
        onView(withId(R.id.tvPoruka)).check(matches(isDisplayed()))
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToFirst())
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToLast())
        onView(withId(R.id.dodajIstrazivanjeDugme)).check(matches(isDisplayed()))
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToFirst())
        onView(withId(R.id.listaAnketa)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(allOf(hasDescendant(withText(ankete[3].naziv)),
            hasDescendant(withText(ankete[3].nazivIstrazivanja))), click()))
        onData(anything()).inAdapterView(withId(R.id.odgovoriLista)).atPosition(1).perform(click())
        onView(allOf(withText(odgovori[1]))).check(matches(not(withTextColor(R.color.answer_click))))
        //onView(allOf(withText(odgovori[1]))).check(matches(withTextColor(R.color.black)))
        onView(withId(R.id.dugmeZaustavi)).perform(click())
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToLast())
        onView(withId(R.id.dodajIstrazivanjeDugme)).check(matches(isDisplayed()))
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToFirst())
        onView(withId(R.id.listaAnketa)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(allOf(hasDescendant(withText(ankete[2].naziv)),
            hasDescendant(withText(ankete[2].nazivIstrazivanja))), click()))
        onView(withId(R.id.filterAnketa)).check(matches(isDisplayed()))  // jer je buduca anketa
    }

    private fun withTextColor(expectedId: Int): Matcher<View?> {
        return object : BoundedMatcher<View?, TextView>(TextView::class.java) {
            override fun matchesSafely(textView: TextView): Boolean {
                val colorId = ContextCompat.getColor(textView.context, expectedId)
                return textView.currentTextColor == colorId
            }

            override fun describeTo(description: Description) {
                description.appendText("with text color: ")
                description.appendValue(expectedId)
            }
        }
    }

}