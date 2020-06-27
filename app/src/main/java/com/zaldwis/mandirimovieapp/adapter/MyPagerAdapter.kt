import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.zaldwis.mandirimovieapp.ReviewFragment
import com.zaldwis.mandirimovieapp.VideoFragment

class MyPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val pages = listOf(
        ReviewFragment(),
        VideoFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

}