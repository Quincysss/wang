using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class NewS : MonoBehaviour
{
    Button button;
    public GameObject panel;
    public move player;
    void Start()
    {
        button = GetComponent<Button>();
        button.onClick.AddListener(OnClick);
    }

    // Update is called once per frame
    void OnClick()
    {
        SceneManager.LoadScene("SampleScene");
        panel.SetActive(false);
        Time.timeScale = 1;
        player.enabled = true;
    }
}
