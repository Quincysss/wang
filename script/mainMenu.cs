using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class mainMenu : MonoBehaviour
{
    public GameObject Store;
    public GameObject house;
    public GameObject own;
    public void toExit()
    {
        Application.Quit();
    }

    public void toStart()
    {
        SceneManager.LoadScene("Game");
    }

    public void toHouse()
    {
        own.SetActive(false);
        house.SetActive(true);
    }

    public void toStore()
    {
        own.SetActive(false);
        Store.SetActive(true);
    }
}
