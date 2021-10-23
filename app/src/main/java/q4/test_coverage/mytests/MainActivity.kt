package q4.test_coverage.mytests

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import q4.test_coverage.mytests.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val emailValidator = EmailValidator()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(layoutInflater).also {
            _binding = it
            setContentView(binding.root)

            with(binding) {
                emailInput.addTextChangedListener(emailValidator)
                saveButton.setOnClickListener {
                    if (emailValidator.isValid) {
                        Toast.makeText(
                            this@MainActivity,
                            getString(R.string.valid_email), Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val errorEmail = getString(R.string.invalid_email)
                        emailInput.error = errorEmail
                        Toast.makeText(
                            this@MainActivity, errorEmail,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}