using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class ToMain : MonoBehaviour
{
    public Button button;
    public GameObject panel;
    public move player;
    public score score;

    void Start()
    {
        Button btn = button.GetComponent<Button>();
        btn.onClick.AddListener(OnClick);
    }
    void OnClick()
    {
        Time.timeScale = 1;
        SceneManager.LoadScene("menu");
    }
}
